import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Animal{
    private String name;
    private int age;
    private double[] mealSize=new double[3]; 
    private static HashMap<String,Animal> nameHash = new HashMap<String,Animal>();
    private String cleanMessage;

    public void setMessage(String message){cleanMessage=message;}

    public String getMessage(){return cleanMessage;}

    public int getAge(){return age;}

    public double[] getMealSize(){
        return mealSize;
        }

    public void setMealSize(double[] values){mealSize=values;}

    public Animal(String name,int age){
        this.name=name;
        this.age=age;
        //hash map to find the object by name
        nameHash.put(name,this);
    }

    public static Animal findByName(String name){return nameHash.get(name);}
}

class Lion extends Animal{
    public Lion(String name,int age){
        super(name,age);
        setMealSize(findMealSize());
        setMessage("Cleaning %s's habitat: Removing bones and refreshing sand.");
    }

    public double[] findMealSize(){
        double dif=5-getAge();
        return new double[]{5-dif*0.05,0,0};
    }
}

class Elephant extends Animal{
    public Elephant(String name,int age){
        super(name,age);
        setMealSize(findMealSize());
        setMessage("Cleaning %s's habitat: Washing the water area.");
    }

    public double[] findMealSize(){
        double dif=20-getAge();
        return new double[]{0,0,10-dif*0.015};
    }
}

class Penguin extends Animal{
    public Penguin(String name,int age){
        super(name,age);
        setMealSize(findMealSize());
        setMessage("Cleaning %s's habitat: Replenishing ice and scrubbing walls.");
    }

    public double[] findMealSize(){
        double dif=4-getAge();
        return new double[]{0,3-dif*0.04,0};
    }
}

class Chimpanzee extends Animal{
    public Chimpanzee(String name,int age){
        super(name,age);
        setMealSize(findMealSize());
        setMessage("Cleaning %s's habitat: Sweeping the enclosure and replacing branches.");
    }

    public double[] findMealSize(){
        int dif = 10-getAge();
        double newAmount=3-dif*0.0125;
        return new double[]{newAmount,0,newAmount};
    }
}