import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.io.*;

public class Zoo{
    private static double meat;
    private static double plant;
    private static double fish;
    //collects the outputData while system working in the end of the each file writes down the outputData data to a text file
    private static List<String> outputData=new ArrayList<>();

    public static void reader(String file){
        Locale.setDefault(Locale.US);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            List<String> lines= new ArrayList<>();
            //converts every line as an commnad then splits in to parts to proccess command
            while((line = br.readLine()) != null){lines.add(line);}
            for(int i=0;lines.size()>i;i++){
                String[] parts = lines.get(i).split(",");
                //special part for initialization parts
                if(i==0){
                    switch(parts[0]){
                        case "Lion":
                        case "Elephant":
                        case "Chimpanzee":
                        case "Penguin":
                            outputData.add("***********************************");
                            outputData.add("***Initializing Animal information***");
                            break;
                        case "Visitor":
                        case "Personnel":
                            outputData.add("***********************************");
                            outputData.add("***Initializing Visitor and Personnel information***");
                            break;
                        case "Meat":
                        case "Fish":
                        case "Plant":
                            outputData.add("***********************************");
                            outputData.add("***Initializing Food Stock***");
                            break;
                    }
                }
                //reads the first part to decide operation
                switch(parts[0]){
                    case "Lion":
                        new Lion(parts[1],Integer.parseInt(parts[2]));
                        outputData.add(String.format("Added new Lion with name %s aged %s.",parts[1],parts[2]));
                        break;
                    case "Elephant":
                        new Elephant(parts[1],Integer.parseInt(parts[2]));
                        outputData.add(String.format("Added new Elephant with name %s aged %s.",parts[1],parts[2]));
                        break;
                    case "Chimpanzee":
                        new Chimpanzee(parts[1],Integer.parseInt(parts[2]));
                        outputData.add(String.format("Added new Chimpanzee with name %s aged %s.",parts[1],parts[2]));
                        break;
                    case "Penguin":
                        new Penguin(parts[1],Integer.parseInt(parts[2]));
                        outputData.add(String.format("Added new Penguin with name %s aged %s.",parts[1],parts[2]));
                        break;
                    case "Visitor":
                        new Visitor(parts[1],parts[2]);
                        outputData.add(String.format("Added new Visitor with id %s and name %s.",parts[2],parts[1]));
                        break;
                    case "Personnel":
                        new Personnel(parts[1],parts[2]);
                        outputData.add(String.format("Added new Personnel with id %s and name %s.",parts[2],parts[1]));
                        break;
                    case "Meat":
                        meat=Double.parseDouble(parts[1]);
                        outputData.add(String.format("There are %.3f kg of Meat in stock",meat));
                        break;
                    case "Fish":
                        fish=Double.parseDouble(parts[1]);
                        outputData.add(String.format("There are %.3f kg of Fish in stock",fish));
                        break;
                    case "Plant":
                        plant=Double.parseDouble(parts[1]);
                        outputData.add(String.format("There are %.3f kg of Plant in stock",plant));
                        break;
                    case "List Food Stock":
                        outputData.add("***********************************");
                        outputData.add("***Processing new Command***");
                        outputData.add("Listing available Food Stock:");
                        outputData.add(String.format("Plant: %.3f kgs",plant));
                        outputData.add(String.format("Fish: %.3f kgs",fish));
                        outputData.add(String.format("Meat: %.3f kgs",meat));
                        break;
                    case "Animal Visitation":
                        visit(parts[1],parts[2]);
                        break;
                    case "Feed Animal":
                        try{
                            feed(parts[1],parts[2],parts[3]); 
                        }catch(UnAuthorizedException e){
                            outputData.add("Error: "+e.getMessage());
                        }catch(NotEnoughException e){
                            outputData.add("Error: "+e.getMessage());
                        }catch(NumberFormatException e){
                            outputData.add(String.format("Error processing command: Feed Animal,%s,%s,%s",parts[1],parts[2],parts[3]));
                            outputData.add("Error:"+e.getMessage());
                        }
                        break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void output(String fileName){
        Locale.setDefault(Locale.US);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for(int i=0;i<outputData.size();i++){
                writer.write(outputData.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void feed(String ID,String animalName , String numberOfMeals){
        Locale.setDefault(Locale.US);
        outputData.add("***********************************");
        outputData.add("***Processing new Command***");
        People person=null;
        Animal animal=null;
        try{
            person=People.findByID(ID);
            animal=Animal.findByName(animalName);
            double meatNeed= animal.getMealSize()[0]*Integer.parseInt(numberOfMeals);
            double fishNeed= animal.getMealSize()[1]*Integer.parseInt(numberOfMeals);
            double plantNeed= animal.getMealSize()[2]*Integer.parseInt(numberOfMeals);
            //first chechks for authorization then procceds by checking the animals kind for proper operation
            if(person instanceof Visitor){
                outputData.add(String.format("%s tried to feed %s",person.getName(),animalName));
                throw new UnAuthorizedException("Visitors do not have the authority to feed animals.");
            }
            outputData.add(String.format("%s attempts to feed %s.",person.getName(),animalName));
            if(animal instanceof Lion){
                if(meatNeed>meat){
                    throw new NotEnoughException("Not enough Meat");
                }
                meat=meat-meatNeed;
                outputData.add(String.format("%s has been given %.3f kgs of meat",animalName,meatNeed));
            }
            if(animal instanceof Elephant){
                if(plantNeed>plant){
                    throw new NotEnoughException("Not enough Plant");
                }
                plant=plant-plantNeed;
                outputData.add(String.format("%s has been given %.3f kgs assorted fruits and hay",animalName,plantNeed));
            }
            if(animal instanceof Penguin){
                if(fishNeed>fish){
                    throw new NotEnoughException("Not enough Fish");
                }
                fish=fish-fishNeed;
                outputData.add(String.format("%s has been given %.3f kgs of various kinds of fish",animalName,fishNeed));
            }
            if(animal instanceof Chimpanzee){
                if(meatNeed>meat){
                    throw new NotEnoughException("Not enough Meat");
                }
                if(plantNeed>plant){
                    throw new NotEnoughException("Not enough Plant");
                }
                meat=meat-meatNeed;
                plant=plant-plantNeed;
                outputData.add(String.format("%s has been given %.3f kgs of meat and %.3f kgs of leaves",animalName,meatNeed,plantNeed));
            }
        }catch(NullPointerException e){
            if(animal==null){
                outputData.add(String.format("Error: There are no animals with the name %s.",animalName));
            }else{
                outputData.add(String.format("Error: There are no visitors or personnel with the id %s",ID));
            }
        }
    }

    public static void visit(String ID, String animalName){
        Locale.setDefault(Locale.US);
        outputData.add("***********************************");
        outputData.add("***Processing new Command***");
        People person = null;
        Animal animal=null;
        try{
            person = People.findByID(ID);
            animal = Animal.findByName(animalName);
            if(person instanceof Visitor){
                outputData.add(String.format("%s tried  to register for a visit to %s.",person.getName(),animalName));
                outputData.add(String.format("%s successfully visited %s.",person.getName(),animalName));
            }
            else{
                outputData.add(String.format("%s attempts to clean %s's habitat.",person.getName(),animalName));
                outputData.add(String.format("%s started cleaning %s's habitat.",person.getName(),animalName));
                if(animal instanceof Lion){
                    outputData.add(String.format(animal.getMessage(),animalName));
                }
                else if(animal instanceof Elephant){
                    outputData.add(String.format(animal.getMessage(),animalName));
                }
                else if(animal instanceof Penguin){
                    outputData.add(String.format(animal.getMessage(),animalName));
                }
                else{
                    outputData.add(String.format(animal.getMessage(),animalName));
                }
            }
        }catch(NullPointerException e){
            if(animal==null){
                outputData.subList(outputData.size() - 2, outputData.size()).clear();
                outputData.add(String.format("%s attempts to clean %s's habitat.",person.getName(),animalName));
                outputData.add(String.format("Error: There are no animals with the name %s.",animalName));
            }else{
                outputData.add(String.format("Error: There are no visitors or personnel with the id %s",ID));
            }
        }
    }
}