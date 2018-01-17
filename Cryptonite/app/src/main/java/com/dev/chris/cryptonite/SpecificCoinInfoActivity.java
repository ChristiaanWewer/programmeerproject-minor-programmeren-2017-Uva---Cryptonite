package com.dev.chris.cryptonite;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class SpecificCoinInfoActivity extends AppCompatActivity {

//    private Socket mSocket;
//    {
//        try {
//            mSocket = IO.socket("https://streamer.cryptocompare.com/");
//        } catch (URISyntaxException e) {}
//    }
//
//
//    private Emitter.Listener onNewMessage = args -> {
//
////jhg
//        //hg
//        String message = args[0].toString();
//        String messageType = message.substring(0, message.indexOf("~"));
//        Log.d("Message", message);
//        Log.d("message", messageType);
//
//    };
//
//
//    private String subscriptions[] = {"5~CCCAGG~BTC~USD", "5~CCCAGG~ETH~USD"};
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_specific_coin_info);
//
////        subHashmap.put("subs", subscriptions);
////        mSocket.connect();
////        mSocket.emit("SubAdd", subHashmap);
////        mSocket.on("m", onNewMessage);
//
//
//        mSocket.on(Socket.EVENT_CONNECT, args -> {
//            Map<String, Object> eventArgs = new HashMap<>();
//
//            eventArgs.put("subs", Arrays.asList(subscriptions));
//
//            mSocket.emit("SubAdd", eventArgs);
//        })
//                .on("m", args -> Stream.of(args).forEach(arg -> {
//                    // Log the raw message for debug
//                    Log.d("Raw message: ", arg.toString());
//
//
//                }))
//                .on(Socket.EVENT_DISCONNECT, args -> Log.d("Received disconnect event", "Received disconnect event"));
//
//        mSocket.connect();
//
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_specific_coin_info);


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.addToBackStack(null);

        SpecificCoinListFragment specificCoinListFragment = new SpecificCoinListFragment();
        ft.replace(R.id.specific_crypto_fragment_container, specificCoinListFragment);
        ft.commit();

    }


}
