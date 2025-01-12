package com.github.jtsolvtransactions.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jtsolvtransactions.model.BankBalance;
import com.github.jtsolvtransactions.model.JsonSerde;
import com.github.jtsolvtransactions.topology.BankBalanceTopology;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.HostInfo;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class BankBalanceRepository extends GenericKafkaStreamsRepository<Long, BankBalance> {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public BankBalanceRepository(
            HostInfo hostInfo,
            KafkaStreams kafkaStreams
    ) {
        super();
        super.setKeySerde(Serdes.Long());
        super.setValueSerde(new JsonSerde<>(BankBalance.class));
        super.setHostInfo(hostInfo);
        super.setKafkaStreams(kafkaStreams);
        super.setStoreName(BankBalanceTopology.BANK_BALANCES_STORE);
        super.setFindRemotelyUri("/bank-balance/%s");
    }

    @Override
    protected BankBalance findRemotely(Long key, HostInfo hostInfo) {
        dbg("Finding Bank Balance with key {} remotely in host {}" + key + hostInfo);
        var url = "http://%s:%d" + super.getFindRemotelyUri();
        var urlWithParams = url.formatted(hostInfo.host(), hostInfo.port(), key.toString());
        var okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(urlWithParams).build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            return OBJECT_MAPPER.readValue(Objects.requireNonNull(response.body()).string(), BankBalance.class);
        } catch (Exception e) {
            throw new RuntimeException("Exception reading bank balance from remote server");
        }
//        return restTemplate.getForObject(urlWithParams, BankBalance.class);
    }

    private static void dbg(String txt){
        System.out.println(txt);
    }

}
