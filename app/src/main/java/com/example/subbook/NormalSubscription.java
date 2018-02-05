package com.example.subbook;

import java.util.Date;

/**
 * Created by heesoopark on 2018-02-04.
 */

public class NormalSubscription extends Subscription {
    NormalSubscription(String name, String date, double charge) {
        super(name, date, charge);
    }

    NormalSubscription(String name, String date, double charge, String comment) {
        super(name, date, charge, comment);
    }


}
