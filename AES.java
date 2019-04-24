package pod4;


import static java.lang.Character.getNumericValue;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/*PLAN DZIAŁANIA*
1. dostajemy  STRING wiadomosc i STRING klucz
2. Zamieniamy klucz na Byte z pomocą .getBytes();
3. for{
    4. Bierzemy pierwszy blok wiadomości z pomocą getByte
    4.5. Klucz na getByte()
    5. Robimy XOR bitów klucza i wiadomości
    6. dołączamy ten blok wiadomości do WYNIKU
    7. podmieniamy klucz na ten fragment
}*/


public class POD4 {
    
    public static String XOR(byte[] plain, byte[] key){
        System.out.println(DatatypeConverter.printHexBinary(plain));
        return "a";
    }


    public static byte[] dajByte(String plain, int odktorej){
        String pomocnik0="";
        for(int i = odktorej ; i < odktorej + 16 ; i++){
            pomocnik0+=plain.charAt(i);
        }
        byte[] wynik = pomocnik0.getBytes();
        return wynik;
    }
    public static String dajString(byte[] plain){
        String wiadomosc=(DatatypeConverter.printHexBinary(plain));
        //System.out.println("Wiadomosc: "+wiadomosc);
        String wynik="";    
        for(int i=0;i<wiadomosc.length();i=i+2){
            int x=getNumericValue(wiadomosc.charAt(i));
            int y=getNumericValue(wiadomosc.charAt(i+1));
            int z=x*16+y;
            wynik+=((char)z);
        }
        return wynik;
    }
    
    
    public static byte[] AES(byte[] plain, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        // szyfrowanie
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(plain);
        return encrypted;
    }
    public static byte[] SEA(byte[] plain, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        // deszyfrowanie
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(plain);
        return decrypted;
    }
    
    
    
    
    public static void main(String[] args) throws Exception {

        //  wstęp
        Scanner sc=new Scanner(System.in);
        String SPlain="abcdefghij";
        for(int i=0;i<159;i++){
            SPlain+="abcdefghij";
        }
        String SKey="abcdefghijklmnop";
        while(SPlain.length()%16!=0) SPlain+="x";//MUSI
        while(SKey.length()%16!=0) SKey+="x";//TO TEŻ
        if(SKey.length()>16){//zmniejszanie zbyt dużego klucza
            String SKey2="";
            for(int i=0;i<16;i++){
                SKey2+=SKey.charAt(i);
            }      
            SKey=SKey2;
        }
        
        byte[] plain = SPlain.getBytes();
        byte[] key = SKey.getBytes();//16 znaków
        
        
        // szyfrowanie
        long startTime1 = System.nanoTime();
        byte encrypted[] = AES(plain, key);
        System.out.println(encrypted);
        System.out.println(dajString(encrypted)+"\n");
        long estimatedTime1 = System.nanoTime() - startTime1;
        //System.out.println("ECB szyfrowanie: "+estimatedTime1+" nanosekund");
        

        // deszyfrowanie
        long startTime2 = System.nanoTime();
        encrypted[1]++;
        byte decrypted[] = SEA(encrypted, key);
        System.out.println(decrypted);
        System.out.println(dajString(decrypted)+"\n");
        long estimatedTime2 = System.nanoTime() - startTime2;
        //System.out.println("ECB deszyfrowanie: "+estimatedTime2+" nanosekund");
    }
}



/*{//AES
        Scanner sc=new Scanner(System.in);
        System.out.println("Podaj wiadomosc: ");
        String SPlain=sc.next();
        System.out.println("Podaj klucz: ");
        String SKey=sc.next();
        while(SPlain.length()%16!=0) SPlain+="x";
        while(SKey.length()%16!=0) SKey+="x";
        
        
        
        byte[] plain = SPlain.getBytes();
        byte[] key = SKey.getBytes();//16 znaków
        System.out.println(plain);
        System.out.println(dajString(plain)+"\n");

        // szyfrowanie
        byte encrypted[] = AES(plain, key);
        System.out.println(encrypted);
        System.out.println(dajString(encrypted)+"\n");
        
        // deszyfrowanie
        byte decrypted[] = SEA(encrypted, key);
        System.out.println(decrypted);
        System.out.println(dajString(decrypted)+"\n");
}*/
