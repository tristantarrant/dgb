package it.redhat.dgb.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectSizeFetcher {

    public static long getObjectSize(Object o) {
        long objectSize = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();
            objectSize = baos.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectSize;
    }
}
