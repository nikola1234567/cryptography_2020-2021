package algorithm;

import javax.print.attribute.HashAttributeSet;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class SubstitutionCryptography {


    String[] forbidden_signs2;
    Hashtable<String, String> permutation = new Hashtable<>();

    /*
    * Konstruktor vo koj se inicijalizira niza od zabraneti znaci
    * Zabraneti znaci - tie so se brisat od tekstot
    * Se definira i permutacija so koja se kriptija tekstot
    * */
    public SubstitutionCryptography() {

        forbidden_signs2 = new String[] {"\\.", ",", ";", ":", "!", "@", "#", "$", "%", "^", "&", "\\*", "\\(",
                "\\)", "-", "_", "=", "\\+", "\\[", "\\{", "\\]", "\\}", "|", "'\''", "\"", "<",
                ">", "\\?", "/", " "};

        permutation.put("А","С");
        permutation.put("Б","Ј");
        permutation.put("В","Е");
        permutation.put("Г","Ф");
        permutation.put("Д","З");
        permutation.put("Ѓ","О");
        permutation.put("Е","Ќ");
        permutation.put("Ж","А");
        permutation.put("З","У");
        permutation.put("Ѕ","И");
        permutation.put("И","Џ");
        permutation.put("Ј","Т");
        permutation.put("К","В");
        permutation.put("Л","М");
        permutation.put("Љ","Г");
        permutation.put("М","Ц");
        permutation.put("Н","К");
        permutation.put("Њ","Б");
        permutation.put("О","Ч");
        permutation.put("П","Љ");
        permutation.put("Р","Њ");
        permutation.put("С","Ѓ");
        permutation.put("Т","Ж");
        permutation.put("Ќ","Ш");
        permutation.put("У","Ѕ");
        permutation.put("Ф","Р");
        permutation.put("Х","П");
        permutation.put("Ц","Д");
        permutation.put("Ч","Л");
        permutation.put("Џ","Н");
        permutation.put("Ш","Х");

    }

    /*
    * param: source - patekata do datotekata kaj so se naogja tekstot za enkriptiranje
    * param: destination - pateka do datotekat avo koja go stvame enkriptiraniot tekst
    * return: void - rezultatot e smesten vo destinaciskat adatoteka
    * */
    public void encryptText(String source, String destination) throws IOException {
        File f=new File(source);
        File f1=new File(destination);
        BufferedReader br=null;
        BufferedWriter bw=null;
        try{
            Reader reader=new FileReader(f);
            br=new BufferedReader(reader);

            Writer writer=new FileWriter(f1);
            bw=new BufferedWriter(writer);
            PrintWriter pw=new PrintWriter(bw);

            String transformed=null;
            String line=br.readLine();
            while(line!=null){
                transformed=transform(line);
                pw.println(transformed);
                line=br.readLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(br!=null){
                br.close();
            }
            if(bw!=null){
                bw.flush();
                bw.close();
            }
        }
    }

    /*
    * param: what - niza od Stringovi koja sluzi koi bukvi da se zamenat vo tekstot
    * param: to - niza od Stringovi koja sluzi vo koi bukvi da se smenat
    * param: sourceTextPath - pateka do datotekat akade sto se naogja text-ot vo koj kje se vrsat promenite
    * +: i-tata bukva od what parametarot se zamenuva so i-tata bukva od to parametarot
    * return: void - pecati na konzola tekstot so promeni, kade promenite se vneseni so upperCase za
    * distinkcija da se znae deka tie se smeneti
    * */
    public void resultOfReplacing(String[] what, String[] to, String sourceTextPath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(sourceTextPath)));

        String line = br.readLine();
        String res = "";
        while (line != null) {
            String res1 = line;
            for (int i = 0; i<what.length;i++){
                res1 = res1.replaceAll(what[i], to[i].toUpperCase());
            }
            res += res1;
            line = br.readLine();
        }

        System.out.println(res);

        br.close();
    }

    /*
    * param: line - String koj se transformira
    * return: String - Novata linija so izbrisani specijalni znaci i enkriptirana
    * */
    private String transform(String line) {
        for (String character: this.forbidden_signs2) {
            line = line.replaceAll(character, "");
        }
        return this.encryptLine(line);
    }

    /*
    * param: line - String koj kje se enkriptira
    * return: String - enkriptiran vlezniot String spored postavenat apermutacija
    * */
    private String encryptLine(String line){
        String res = "";
        for (int i =0 ; i<line.length();i++){
            res += this.permutation.get(Character.toString(line.charAt(i)).toUpperCase());
        }
        return res.toLowerCase();
    }

    /*
    * param: sourceTextPath - pateka do datotekata kaj so se naogja text
    * param: destinationFile - pateka do datoteka kaj so kje se zacuva rezultatot
    * return: void - generiranite frekvencii za text-ot vo datotekata na prviot parametar,
    * a rezulataot se naogja vo dadtotekat na vtoriot parametar
    * */
    public void generateFrequencies(String sourceTextPath, String destinationFile) throws IOException {
        Hashtable<String, Float> frequencies = this.calculateFrequencyForEachLetter(sourceTextPath);
        ArrayList<Map.Entry<?, Float>> l = sortValue(frequencies);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(destinationFile))));

        for (Map.Entry<?, Float> entry: l) {
            pw.println(String.format("Letter: %s, Frequency: %f", entry.getKey(), entry.getValue()));
            pw.flush();
        }

        pw.close();
    }


    /*
    * param: sourceTextPath - pateka do datotekata kaj so se naogja text
    * return: Hashtable<String, Float> - hesh tabela vo koja sekoja bukva e mapirana so svojata frekvencija
    * */
    private Hashtable<String, Float> calculateFrequencyForEachLetter(String sourceTextPath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(sourceTextPath)));
        Hashtable<String, Float> res = new Hashtable<>();

        float totalNumberOfLetters = 0;
        String line = br.readLine();

        while (line != null) {

            for (int i = 0; i < line.length(); i++){
               String currentLetter = Character.toString(line.charAt(i));
               float currentLetterCount = res.get(currentLetter) == null ? 0f : res.get(currentLetter);
               res.put(currentLetter, currentLetterCount + 1);
               totalNumberOfLetters++;
            }

            line = br.readLine();
        }

        br.close();

        DecimalFormat df = new DecimalFormat("###.####");
        for (String letter : res.keySet()) {
            res.put(letter, Float.valueOf(df.format(res.get(letter) / totalNumberOfLetters)));
        }

        return res;
    }

    /*
    * param: Hashtable<String,Float> - tabela od bukvi so svoi frekvencii
    * return: ArrayList<Map.Entry<?, Float>> - lista od sortirani key-value pairs spored value od
    * vleznata tabela
    * */
    private static ArrayList<Map.Entry<?, Float>> sortValue(Hashtable<String, Float> t){

        ArrayList<Map.Entry<?, Float>> l = new ArrayList(t.entrySet());
        Collections.sort(l, new Comparator<Map.Entry<?, Float>>(){

            public int compare(Map.Entry<?, Float> o1, Map.Entry<?, Float> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }});

        return l;
    }
}
