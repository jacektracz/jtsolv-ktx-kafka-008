package com.github.jtsolvtransactions.repository;

import com.github.jtsolvtransactions.repository.exceptions.ObjectNotFoundException;


import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import java.util.Optional;



public abstract class GenericKafkaStreamsRepository<K,V> {
    private  Serde<K> keySerde;
    private  Serde<V> valueSerde;
    private  HostInfo hostInfo;
    private  KafkaStreams kafkaStreams;
    private String storeName;
    private String findRemotelyUri;

    protected GenericKafkaStreamsRepository(){}
    protected GenericKafkaStreamsRepository(Serde<K> keySerde, Serde<V> valueSerde, HostInfo hostInfo, KafkaStreams kafkaStreams, String storeName) {
        this.keySerde = keySerde;
        this.valueSerde = valueSerde;
        this.hostInfo = hostInfo;
        this.kafkaStreams = kafkaStreams;
        this.storeName = storeName;
    }

    public Serde<K> getKeySerde() {
        return keySerde;
    }

    public Serde<V> getValueSerde() {
        return valueSerde;
    }

    public HostInfo getHostInfo() {
        return hostInfo;
    }

    public KafkaStreams getKafkaStreams() {
        return kafkaStreams;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getFindRemotelyUri() {
        return findRemotelyUri;
    }

    public void setFindRemotelyUri(String findRemotelyUri) {
        this.findRemotelyUri = findRemotelyUri;
    }


    public void setKeySerde(Serde<K> keySerde) {
        this.keySerde = keySerde;
    }

    public void setValueSerde(Serde<V> valueSerde) {
        this.valueSerde = valueSerde;
    }

    public void setHostInfo(HostInfo hostInfo) {
        this.hostInfo = hostInfo;
    }

    public void setKafkaStreams(KafkaStreams kafkaStreams) {
        this.kafkaStreams = kafkaStreams;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }


    public V find(K key) {
        var metadata = kafkaStreams.queryMetadataForKey(storeName, key, keySerde.serializer());
        var activeHost = metadata.activeHost();
        if (hostInfo.equals(activeHost)) return findLocally(key);
        return findRemotely(key, activeHost);
    }

    private V findLocally(K key) {
        dbg("Looking for object with key: {}, locally" + key);
        return Optional
                .ofNullable(getStore().get(key))
                .orElseThrow(() -> new ObjectNotFoundException(key, storeName));

    }

    private ReadOnlyKeyValueStore<K, V> getStore() {
        return kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(
                        storeName,
                        QueryableStoreTypes.keyValueStore()));
    }


    protected abstract V findRemotely(K key, HostInfo hostInfo);

    private static void dbg(String txt){
        System.out.println(txt);
    }

}
