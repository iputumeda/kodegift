package id.co.meda.survey.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by Farisi on 21/07/2016.
 */
public class Utility {
    public static final String TAG = "TAG";
    public static List<byte[]> getListFromArrayOfByte(byte[] bytes){
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        List<byte[]> output = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            output = (List<byte[]>) ois.readObject();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        } finally {
            if(bais != null){
                try {
                    bais.close();
                } catch (IOException e) {
                }
            }
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
            return output;
        }
    }
    public static byte[] getArrayOfByteFromList(List<byte[]> list){
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        byte[] output = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(list);
            output = baos.toByteArray();
        } catch (IOException e) {
        } finally {
            if(baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                }
            }
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
            return output;
        }
    }
}
