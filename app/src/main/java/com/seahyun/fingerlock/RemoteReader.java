/*
 * Copyright 2016 Anton Tananaev (anton.tananaev@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seahyun.fingerlock;

import android.util.Base64;
import android.util.Log;

import com.tananaev.adblib.AdbBase64;
import com.tananaev.adblib.AdbConnection;
import com.tananaev.adblib.AdbCrypto;
import com.tananaev.adblib.AdbStream;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

public class RemoteReader implements Reader, Serializable {

    private static final String TAG = RemoteReader.class.getSimpleName();

    private KeyPair keyPair;

    private boolean onPostExec;

    AdbConnection connection = null;

    public RemoteReader(KeyPair keyPair) {
        this.keyPair = keyPair;
        onPostExecute(false);
    }


    @Override
    public void onPostExecute(boolean onPostExecute) {
        onPostExec =  onPostExecute;
    }

    @Override
    public void create() {

        onPostExecute(false);
        Log.d("remote reader","1");

        try {

            Socket socket = new Socket("localhost", 5555);
            Log.d("remote reader","2");

            AdbCrypto crypto = AdbCrypto.loadAdbKeyPair(new AdbBase64() {
                @Override
                public String encodeToString(byte[] data) {
                    return Base64.encodeToString(data, Base64.DEFAULT);
                }
            }, keyPair);
            Log.d("remote reader","3");

            connection = AdbConnection.create(socket, crypto);
            Log.d("remote reader","4");

            connection.connect();
            Log.d("remote reader","5");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void read(UpdateHandler updateHandler) {

//        AdbConnection connection = null;
        onPostExecute(false);
        Log.d("remote reader 22222","read start");

        try {

//            updateHandler.update(R.string.status_connecting, null);
//            Log.d("remote reader","1");
//            Socket socket = new Socket("localhost", 5555);
//            Log.d("remote reader","2");
//            AdbCrypto crypto = AdbCrypto.loadAdbKeyPair(new AdbBase64() {
//                @Override
//                public String encodeToString(byte[] data) {
//                    return Base64.encodeToString(data, Base64.DEFAULT);
//                }
//            }, keyPair);
//            Log.d("remote reader","3");
//            connection = AdbConnection.create(socket, crypto);
//            Log.d("remote reader","4");
//            connection.connect();
//            Log.d("remote reader","5");

            updateHandler.update(R.string.status_opening, null);
            Log.d("remote reader","6");

            AdbStream stream = connection.open("shell:logcat -v time");
            Log.d("remote reader","7");

            updateHandler.update(R.string.status_active, null);
            Log.d("remote reader","8");

            while (!onPostExec/*!updateHandler.isCancelled()*/) {
                List<String> lines = new ArrayList<>();
                for (String line : new String(stream.read()).split("\\r?\\n")) {
                    if (!line.isEmpty()) {
                        lines.add(line);
                    }
                }
                updateHandler.update(0, lines);
            }

//            if(onPostExec){
//                connection.close();
//            }


        } catch (InterruptedException e) {
            try {
                connection.close();
            } catch (IOException ee) {
                Log.w(TAG, ee);
            }
        } catch (IOException e) {
            Log.w(TAG, e);
        }

    }



}
