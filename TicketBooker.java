import java.util.*;
public class TicketBooker
{
    //berths and ticket given in static coz they r common for all passengers
    static int availableLowerBerths = 21;
    static int availableMiddleBerths = 21;
    static int availableUpperBerths = 21;
    static int availableRacTickets = 18;
    static int availableWaitingList = 10;
    //queue is used coz the first person who books the ticket will get the ticket first
    static Queue<Integer> waitingList = new LinkedList<>();
    static Queue<Integer> racList =  new LinkedList<>();
    static List<Integer> bookedTicketList =  new ArrayList<>();

    static List<Integer> lowerBerthsPositions = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));
    static List<Integer> middleBerthsPositions = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));
    static List<Integer> upperBerthsPositions = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));
    static List<Integer> racPositions = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18));
    static List<Integer> waitingListPositions = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
    //map used to give connection b/w passengrt id and passenger
    static Map<Integer, Passenger> passengers = new HashMap<>();

    public void bookTicket(Passenger p,int berthInfo,String allotedBerth)
    {
        p.number=berthInfo;
        p.alloted= allotedBerth;
        passengers.put(p.passengerId,p);
        bookedTicketList.add(p.passengerId);
        System.out.println("Booked Successfully");
    }
    public void addToRAC(Passenger p,int racInfo,String allotedRAC)
    {
        p.number=racInfo;
        p.alloted=allotedRAC;
        passengers.put(p.passengerId,p);
        racList.add(p.passengerId);//recList is a queue
        availableRacTickets--;
        racPositions.remove(0);
        System.out.println("Added to RAC Successfully");
    }
    public void addToWaitingList(Passenger p,int waitingListInfo,String allotedWL)
    {
        p.number=waitingListInfo;
        p.alloted=allotedWL;
        passengers.put(p.passengerId,p);
        waitingList.add(p.passengerId);
        availableWaitingList--;
        waitingListPositions.remove(0);
        System.out.println("Added to Waiting List Successfully");
    }
    public void cancelTicket(int passengerId)
    {
        Passenger p =passengers.get(passengerId);
        passengers.remove(passengerId);
        bookedTicketList.remove(passengerId);
        int positionBooked = p.number;
        System.out.println("Cancelled Successfully");
        if(p.alloted.equals("L"))
        {
            availableLowerBerths++;
            lowerBerthsPositions.add(positionBooked);
        }
        else if(p.alloted.equals("M"))
        {
            availableMiddleBerths++;
            middleBerthsPositions.add(positionBooked);
        }
        else if(p.alloted.equals("U"))
        {
            availableUpperBerths++;
            upperBerthsPositions.add(positionBooked);
        }
        if(racList.size()>0)
        {
           Passenger passengerFromRAC = passengers.get(racList.poll());
            int positionRac = passengerFromRAC.number;
            racPositions.add(positionRac);
            racList.remove(passengerFromRAC.passengerId);
            availableRacTickets++;
            if(waitingList.size()>0)
            {
                Passenger passengerFromWaitingList = passengers.get(waitingList.poll());
                int positionWL = passengerFromWaitingList.number;
                waitingListPositions.add(positionWL);
                waitingList.remove(passengerFromWaitingList.passengerId);
                passengerFromWaitingList.number = racPositions.get(0);
                passengerFromWaitingList.alloted = "RAC";
                racPositions.remove(0);
                racList.add(passengerFromWaitingList.passengerId);
                availableWaitingList++;
                availableRacTickets--;
            }
            Main.bookTicket(passengerFromRAC);  

        }

    }
    public void printAvailable()
    {
        System.out.println("Available Lower Berths "  + availableLowerBerths);
        System.out.println("Available Middle Berths "  + availableMiddleBerths);
        System.out.println("Available Upper Berths "  + availableUpperBerths);
        System.out.println("Availabel RACs " + availableRacTickets);
        System.out.println("Available Waiting List " + availableWaitingList);
        System.out.println("--------------------------");
    }
    public void printPassengers()
    {
        if(passengers.size() == 0)
        {
            System.out.println("No details of passengers");
            return;
        }
        for(Passenger p : passengers.values())
        {
            System.out.println("PASSENGER ID " + p.passengerId );
            System.out.println(" Name " + p.name );
            System.out.println(" Age " + p.age );
            System.out.println(" Status " + p.number + p.alloted);
            System.out.println("--------------------------");
        }
    }
}