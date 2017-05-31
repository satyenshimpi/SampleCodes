package me.satyen.code;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class Serialization {
	
	public static void serializeObject(Object obj) throws Exception{
		FileOutputStream fos = new FileOutputStream("C:\\Users\\Svara\\workspace\\CodingPractise\\src\\me\\satyen\\code\\op.obj");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		fos.close();
	}
	
	public static Object deSerializeObject() throws Exception{
		FileInputStream fis = new FileInputStream("C:\\Users\\Svara\\workspace\\CodingPractise\\src\\me\\satyen\\code\\op.obj");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		ois.close();
		System.out.println(obj.getClass().getSimpleName());
		return obj;
	}
	
	public static String serializeObjectToString(Serializable obj) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.flush();
		return new String(baos.toByteArray());
	}
	
	public static Object deSerializeObjectFromString(String str) throws Exception{
		System.out.println("Deserialising " + str);
		ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
		ObjectInputStream oos = new ObjectInputStream(bais);
		Object o = oos.readObject();
		System.out.println(o.getClass().getSimpleName());
		oos.close();
		return o;
	}
	
    /** Read the object from Base64 string. */
   private static Object fromString( String str ) throws IOException, ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode(str);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
   }

    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
    }
	
	public static void main(String[] args) throws Exception{
		
		String string = toString( new Integer(11) );
        System.out.println(" Encoded serialized version " );
        System.out.println( string );
        Integer some = ( Integer ) fromString( string );
        System.out.println( "\n\nReconstituted object");
        System.out.println( some );

        
        //---------------------
        Serializable obj = new Float(15.0);
		serializeObject(obj);
		System.out.println(deSerializeObject());
		System.out.println("--------------");
		System.out.println(deSerializeObjectFromString(serializeObjectToString(obj)));
	}
}
