import java.util.HashMap;

public class People{
    private String name;
    private String ID;
    private static HashMap<String,People> IDHash = new HashMap<String,People>();


    public People(String name,String ID){
        this.name=name;
        this.ID=ID;
        //hash pam to identify people and find the people object by ID
        IDHash.put(ID,this);
    }

    public static People findByID(String ID){return IDHash.get(ID);}

    public String getName(){return name;}
}

class Visitor extends People{
    public Visitor(String name,String ID){super(name,ID);}
}

class Personnel extends People{
    public Personnel(String name,String ID){super(name,ID);}
}