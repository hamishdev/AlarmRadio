package my.app.goodmorninggamers;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;


public class ThisIsTheOnly_TestClass {

}



class MockBroadcastReceiver extends BroadcastReceiver {

    private static int numTimesCalled = 0;

    MockBroadcastReceiver(){
        numTimesCalled = 0;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        numTimesCalled++;
    }

    public static int getNumTimesCalled() {
        return numTimesCalled;
    }

    public static void setNumTimesCalled(int numTimesCalled) {
        MockBroadcastReceiver.numTimesCalled = numTimesCalled;
    }
}