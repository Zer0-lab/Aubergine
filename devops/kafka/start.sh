if [ ! -f /var/lib/kafka/data/meta.properties ]; then
    uuid=$(kafka-storage random-uuid)
    kafka-storage format --cluster-id $uuid --config /etc/kafka/server.properties.custom
fi

kafka-server-start /etc/kafka/server.properties.custom