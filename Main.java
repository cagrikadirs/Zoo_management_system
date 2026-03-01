public class Main{
    public static void main(String[] args){
       Zoo.reader(args[0]);
       Zoo.reader(args[1]);
       Zoo.reader(args[2]);
       Zoo.reader(args[3]);
       Zoo.output(args[4]);
    }
}

//Exception for Unauthorized operations for visitors
class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String e){super(e);}
}

//Exception to occure when there is no enough food
class NotEnoughException extends RuntimeException{
    public NotEnoughException(String e){super(e);}
}