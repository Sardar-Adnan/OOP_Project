import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

class Ticket {
    private String seatNumber;
    private String ticketType;
    private String departureCity;
    private String arrivalCity;
    private String departureTime;
    private String arrivalTime;
    private double fare;

    public Ticket(String seatNumber, String ticketType, String departureCity, String arrivalCity,
                  String departureTime, String arrivalTime, double fare) {
        this.seatNumber = seatNumber;
        this.ticketType = ticketType;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fare = fare;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getTicketType() {
        return ticketType;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public double getFare() {
        return fare;
    }
}

class Train {
    private String name;
    private String departureLocation;
    private String arrivalLocation;
    private String departureTime;
    private String arrivalTime;
    private double economyClassFare;
    private double businessClassFare;
    private double cargoFare;

    public Train(String name, String departureLocation, String arrivalLocation,
                 String departureTime, String arrivalTime,
                 double economyClassFare, double businessClassFare, double cargoFare) {
        this.name = name;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.economyClassFare = economyClassFare;
        this.businessClassFare = businessClassFare;
        this.cargoFare = cargoFare;
    }

    public String getName() {
        return name;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public double getEconomyClassFare() {
        return economyClassFare;
    }

    public double getBusinessClassFare() {
        return businessClassFare;
    }

    public double getCargoFare() {
        return cargoFare;
    }
}

interface TicketBooking {
    ArrayList<Train> getTrainSchedule();

    void displayScheduleOnUI();

    void bookTicketOnUI(String departureLocation, String arrivalLocation, String ticketType);

    Ticket bookTicket(String departureLocation, String arrivalLocation, String ticketType);
}

abstract class TrainService {
    abstract ArrayList<Train> getTrainSchedule();

   // abstract void displayScheduleOnUI();

    abstract void bookTicketOnUI(String departureLocation, String arrivalLocation, String ticketType);
}

public class TrainManagementSystemGUI extends JFrame implements ActionListener, TicketBooking {
    private JComboBox<String> departureComboBox;
    private JComboBox<String> arrivalComboBox;
    private JComboBox<String> ticketTypeComboBox;
    private JTextArea scheduleTextArea;
    private JTextArea ticketDetailsTextArea;

    private ArrayList<Train> trainSchedule;
    private ArrayList<Ticket> bookedTickets;

    public TrainManagementSystemGUI() {
        setTitle("Train Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230, 230, 255)); // Set background color

        departureComboBox = new JComboBox<>();
        arrivalComboBox = new JComboBox<>();
        ticketTypeComboBox = new JComboBox<>();
        scheduleTextArea = new JTextArea();
        ticketDetailsTextArea = new JTextArea();
        JButton bookTicketButton = new JButton("Book Ticket");

        bookTicketButton.addActionListener(this);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.setBackground(new Color(200, 200, 255)); // Set background color
        inputPanel.add(new JLabel("Departure City:"));
        inputPanel.add(departureComboBox);
        inputPanel.add(new JLabel("Arrival City:"));
        inputPanel.add(arrivalComboBox);
        inputPanel.add(new JLabel("Ticket Type:"));
        inputPanel.add(ticketTypeComboBox);

        JPanel displayPanel = new JPanel(new GridLayout(1, 2));
        displayPanel.add(new JScrollPane(scheduleTextArea));
        displayPanel.add(new JScrollPane(ticketDetailsTextArea));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(200, 200, 255)); // Set background color
        buttonPanel.add(bookTicketButton);

        add(inputPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        initializeData();
        populateComboBoxes();
        displayScheduleOnUI();
    }

    private void initializeData() {
        trainSchedule = new ArrayList<>();
        bookedTickets = new ArrayList<>();

        trainSchedule.add(new Train("Karakoram Express", "Karachi", "Lahore", "08:00 AM", "03:00 PM", 150.0, 250.0, 100.0));
        trainSchedule.add(new Train("Green Line Express", "Lahore", "Islamabad", "10:00 AM", "02:00 PM", 120.0, 200.0, 80.0));
        trainSchedule.add(new Train("Tezgam", "Islamabad", "Peshawar", "12:00 PM", "04:00 PM", 100.0, 180.0, 70.0));
        trainSchedule.add(new Train("Jinnah Express", "Karachi", "Quetta", "02:00 PM", "08:00 PM", 200.0, 300.0, 120.0));
        trainSchedule.add(new Train("Awam Express", "Lahore", "Multan", "04:00 PM", "07:00 PM", 80.0, 150.0, 60.0));
        trainSchedule.add(new Train("Bolan Mail", "Quetta", "Attock", "06:00 PM", "10:00 PM", 50.0, 100.0, 40.0));
    }

    private void populateComboBoxes() {
        for (Train train : trainSchedule) {
            departureComboBox.addItem(train.getDepartureLocation());
            arrivalComboBox.addItem(train.getArrivalLocation());
        }

        ticketTypeComboBox.addItem("Economy Class");
        ticketTypeComboBox.addItem("Business Class");
        ticketTypeComboBox.addItem("Cargo");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Book Ticket")) {
            String departureLocation = departureComboBox.getSelectedItem().toString();
            String arrivalLocation = arrivalComboBox.getSelectedItem().toString();
            String ticketType = ticketTypeComboBox.getSelectedItem().toString();

            bookTicketOnUI(departureLocation, arrivalLocation, ticketType);
        }
    }

    @Override
    public ArrayList<Train> getTrainSchedule() {
        return trainSchedule;
    }

    @Override
    public void displayScheduleOnUI() {
        StringBuilder scheduleText = new StringBuilder();
        scheduleText.append("Train Schedule:\n");

        for (Train train : trainSchedule) {
            scheduleText.append(train.getName()).append(": ").append(train.getDepartureLocation())
                    .append(" to ").append(train.getArrivalLocation()).append(", ")
                    .append(train.getDepartureTime()).append(" - ").append(train.getArrivalTime()).append("\n");
        }

        scheduleText.append("\nFare Types:\n");
        scheduleText.append("Economy Class: Rs. ").append(new DecimalFormat("#.##").format(trainSchedule.get(0).getEconomyClassFare())).append("\n");
        scheduleText.append("Business Class: Rs. ").append(new DecimalFormat("#.##").format(trainSchedule.get(0).getBusinessClassFare())).append("\n");
        scheduleText.append("Cargo: Rs. ").append(new DecimalFormat("#.##").format(trainSchedule.get(0).getCargoFare())).append("\n");

        scheduleTextArea.setText(scheduleText.toString());
    }

    @Override
    public void bookTicketOnUI(String departureLocation, String arrivalLocation, String ticketType) {
        Train selectedTrain = findTrain(departureLocation, arrivalLocation);

        if (selectedTrain != null) {
            Ticket ticket = bookTicket(departureLocation, arrivalLocation, ticketType);
            bookedTickets.add(ticket);

            StringBuilder ticketDetailsText = new StringBuilder();
            for (Ticket bookedTicket : bookedTickets) {
                ticketDetailsText.append("Ticket Details:\n");
                ticketDetailsText.append("Seat Number: ").append(bookedTicket.getSeatNumber())
                        .append("\nDeparture City: ").append(bookedTicket.getDepartureCity())
                        .append("\nArrival City: ").append(bookedTicket.getArrivalCity())
                        .append("\nDeparture Time: ").append(bookedTicket.getDepartureTime())
                        .append("\nArrival Time: ").append(bookedTicket.getArrivalTime())
                        .append("\nTicket Type: ").append(bookedTicket.getTicketType())
                        .append("\nFare: Rs. ").append(new DecimalFormat("#.##").format(bookedTicket.getFare()))
                        .append("\n");
            }
            ticketDetailsTextArea.setText(ticketDetailsText.toString());
        } else {
            ticketDetailsTextArea.setText("No train available for the selected route.");
        }
    }

    @Override
    public Ticket bookTicket(String departureLocation, String arrivalLocation, String ticketType) {
        Random rand = new Random();
        String seatNumber = generateRandomSeatNumber();
        double fare = calculateFare(departureLocation, arrivalLocation, ticketType);

        return new Ticket(seatNumber, ticketType, departureLocation, arrivalLocation,
                getDepartureTime(departureLocation, arrivalLocation),
                getArrivalTime(departureLocation, arrivalLocation), fare);
    }

    private String generateRandomSeatNumber() {
        Random rand = new Random();
        char row = (char) (rand.nextInt(5) + 'A');
        int seat = rand.nextInt(20) + 1;
        return row + String.valueOf(seat);
    }

    private double calculateFare(String departureLocation, String arrivalLocation, String ticketType) {
        Train selectedTrain = findTrain(departureLocation, arrivalLocation);
        if (selectedTrain != null) {
            switch (ticketType) {
                case "Economy Class":
                    return selectedTrain.getEconomyClassFare();
                case "Business Class":
                    return selectedTrain.getBusinessClassFare();
                case "Cargo":
                    return selectedTrain.getCargoFare();
            }
        }
        return 0.0; // Default fare if route not found or ticket type is invalid
    }

    private String getDepartureTime(String departureLocation, String arrivalLocation) {
        Train selectedTrain = findTrain(departureLocation, arrivalLocation);
        return (selectedTrain != null) ? selectedTrain.getDepartureTime() : "";
    }

    private String getArrivalTime(String departureLocation, String arrivalLocation) {
        Train selectedTrain = findTrain(departureLocation, arrivalLocation);
        return (selectedTrain != null) ? selectedTrain.getArrivalTime() : "";
    }

    private Train findTrain(String departureLocation, String arrivalLocation) {
        for (Train train : trainSchedule) {
            if (train.getDepartureLocation().equals(departureLocation)
                    && train.getArrivalLocation().equals(arrivalLocation)) {
                return train;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            TrainManagementSystemGUI gui = new TrainManagementSystemGUI();
            gui.setVisible(true);
        });
    }
}
