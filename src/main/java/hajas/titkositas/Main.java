package hajas.titkositas;

public class Main {


    public static void main(String[] args) { 
        //Meghívjuk az asszimetrikus kulcs generáló osztályt
        GenerateRSA Bob = new GenerateRSA();
        //Meghívjuk a szimmetrikus kulcs generáló osztályt
        GenerateAES Alice = new GenerateAES();
        //Alice megkapja Bob nyilvános kulcsát és Bob kulcsával titkosítja a saját szimmetrikus kulcsát
        byte[] EncryptedDES = Alice.EncryptRSA(Bob.GetPublicKey());
        //Bob megkapja a titkosított kulcsot, majd ezt dekódolja a privát kulcsával
        //Azután meg elmenti Alice szimmetrikus kuilcsát
        Bob.DecryptRSA(EncryptedDES);
        //Bob egy hello üzenetet küld Alice-nek a szimmetrikus kulcs használatával, aki dekódolja a szimetrikus kulcsával az üzenetet
        Alice.ReadMessage(Bob.SendMessage("Hello"));
    }
}
