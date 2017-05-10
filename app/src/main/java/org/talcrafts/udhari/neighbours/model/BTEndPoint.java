package org.talcrafts.udhari.neighbours.model;

import android.bluetooth.le.AdvertiseData;

/**
 * Created by ashis on 5/5/2017.
 */

public class BTEndPoint extends EndPoint {

        public BTEndPoint(String id, String content, String details) {
            super(id);
            this.content = content;
            this.details = details;
            this.type=this.getClass().getSimpleName();
            AdvertiseData data;
        }

        @Override
        public String toString() {
            return content;
        }
    }