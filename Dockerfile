ARG NODE_VERSION=22.15.0
ARG UBUNTU_VERSION=24.10

FROM node:${NODE_VERSION}-slim AS node

FROM ubuntu:${UBUNTU_VERSION} AS app

ARG TARGETPLATFORM
ARG BUILDPLATFORM
ARG JDK_VERSION=23.0.1
ARG MAVEN_VERSION=3.9.9
ARG GOOGLE_JAVA_FORMAT_VERSION=1.27.0
ARG UID=1000
ARG GID=1000
ARG APP_PORT=8080

# Install git
RUN apt-get update && \
    apt-get install -y wget git nano && \
    wget --output-document=/etc/bash_completion.d/git-completion.bash \
        https://raw.githubusercontent.com/git/git/refs/heads/master/contrib/completion/git-completion.bash && \
    echo "source /etc/bash_completion.d/git-completion.bash" >> /etc/bash.bashrc

ENV GIT_EDITOR=nano

# Download java binaries
RUN arch=$(echo ${TARGETPLATFORM} | sed 's/.*\///') && \
    if [ $arch = "amd64" ]; \
        then jdkArch="x64"; \
        else jdkArch="aarch64"; \
    fi && \
    wget --output-document=/tmp/jdk.tar.gz https://download.java.net/java/GA/jdk${JDK_VERSION}/c28985cbf10d4e648e4004050f8781aa/11/GPL/openjdk-${JDK_VERSION}_linux-${jdkArch}_bin.tar.gz && \
    tar --extract --verbose --file=/tmp/jdk.tar.gz --directory=/usr/local && \
    rm /tmp/jdk.tar.gz

# Make java binaries globally available
ENV JAVA_HOME="/usr/local/jdk-${JDK_VERSION}"
ENV PATH="$JAVA_HOME/bin:$PATH"

# Download maven
RUN wget --output-document=/tmp/maven.tar.gz https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz &&\
    tar --extract --verbose --file=/tmp/maven.tar.gz --directory=/usr/local && \
    rm /tmp/maven.tar.gz

# Make maven binaries globally available
ENV MAVEN_HOME="/usr/local/apache-maven-${MAVEN_VERSION}"
ENV MAVEN_OPTS="-Dmaven.repo.local=/opt/apps/maven/repository"
ENV PATH="$MAVEN_HOME/bin:${PATH}"

# Install google java formatter
RUN wget --output-document=/usr/local/lib/google-java-format.jar https://github.com/google/google-java-format/releases/download/v${GOOGLE_JAVA_FORMAT_VERSION}/google-java-format-${GOOGLE_JAVA_FORMAT_VERSION}-all-deps.jar && \
    chmod +x /usr/local/lib/google-java-format.jar

# Install gitmoji & lefthook
COPY --from=node --chown=${UID}:${GID} /usr/local/lib /usr/local/lib
COPY --from=node --chown=${UID}:${GID} /usr/local/include /usr/local/include
COPY --from=node --chown=${UID}:${GID} /usr/local/bin /usr/local/bin

RUN npm install --global gitmoji-cli lefthook git-open

# Create user matching host (permissions issue)
RUN if ! getent group ${GID} > /dev/null; then \
    groupadd -g ${GID} app; fi && \
    if ! getent passwd ${UID} > /dev/null; then \
    useradd -u ${UID} -g ${GID} -m -s /bin/bash app; fi

# Use host user
USER ${UID}:${GID}

# Set working directory
WORKDIR /opt/apps/maven/repository

# Set working directory
WORKDIR /opt/apps/app

# Copy sourcecode
COPY --chown=${UID}:${GID} . .

# Make devops scripts executable
RUN chmod +x ./devops/*.sh

# Expose tomcat server port
EXPOSE ${APP_PORT}

# Default stage to load.
FROM app