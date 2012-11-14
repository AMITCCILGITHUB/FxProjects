package samples.misc;

import com.sun.javafx.collections.annotations.ReturnsUnmodifiableCollection;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import java.util.Random;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
public class Person {
    
    private static ObservableList<Person> testList;
    
    private static final boolean DEBUG = true;
    private static boolean liveUpdate = false;
//    private static Integer MAX_HISTORY = 100;
    public static Integer MAX_HISTORY = 10;
    
    private static long userIDCounter = 1;
    
    public static ObservableList<Person> getTestList() {
        return testList;
    }
    
    static {
        testList = FXCollections.observableArrayList(
            new Person("Jenny", "Bond", false, 23.64),
            new Person("Billy", "James", true, -12.11),
            new Person("Timmy", "Gordon", false, 45),
            new Person("Aiden", "Simpson", false, 0),
            new Person("Jacob", "Grant", true, -92.21),
            new Person("Jackson", "Matthews", false, 0),
            new Person("Ethan", "Beck", false, 48.12),
            new Person("Sophia", "Potts", true, 38.22),
            new Person("Isabella", "Bair", false, 823.43),
            new Person("Olivia", "Fowler", false, -201.23),
            new Person("Jayden", "Walker", true, 49.54),
            new Person("Emma", "Wong", false, -3.49),
            new Person("Chloe", "Samuelsson", false, 0.76),
            new Person("Logan", "Grieve", true, 49.22),
            new Person("Caden", "Sato", false, 90.56),
            new Person("Lilly", "Chin", false, -0.06),
            new Person("Madison", "Barbashov", true, 89.76),
            new Person("Ryan", "Beatty", false, 123.50),
            new Person("Hailey", "Giles", false, 90.56),
            new Person("Molly", "Vos", true, -87.12),
            new Person("Nolan", "Antonio", false, 992.12),
            new Person("Bryce", "Marinacci", false, 1832.29),
            new Person("Maria", "Mayhew", true, -782.12),
            new Person("Lauren", "Holt", false, 291.21),
            
            new Person("Jenny", "Bond", false, 23.64),
            new Person("Billy", "James", true, -12.11),
            new Person("Timmy", "Gordon", false, 45),
            new Person("Aiden", "Simpson", false, 0),
            new Person("Jacob", "Grant", true, -92.21),
            new Person("Jackson", "Matthews", false, 0),
            new Person("Ethan", "Beck", false, 48.12),
            new Person("Sophia", "Potts", true, 38.22),
            new Person("Isabella", "Bair", false, 823.43),
            new Person("Olivia", "Fowler", false, -201.23),
            new Person("Jayden", "Walker", true, 49.54),
            new Person("Emma", "Wong", false, -3.49),
            new Person("Chloe", "Samuelsson", false, 0.76),
            new Person("Logan", "Grieve", true, 49.22),
            new Person("Caden", "Sato", false, 90.56),
            new Person("Lilly", "Chin", false, -0.06),
            new Person("Madison", "Barbashov", true, 89.76),
            new Person("Ryan", "Beatty", false, 123.50),
            new Person("Hailey", "Giles", false, 90.56),
            new Person("Molly", "Vos", true, -87.12),
            new Person("Nolan", "Antonio", false, 992.12),
            new Person("Bryce", "Marinacci", false, 1832.29),
            new Person("Maria", "Mayhew", true, -782.12),
            new Person("Lauren", "Holt", false, 291.21),
            
            new Person("Jenny", "Bond", false, 23.64),
            new Person("Billy", "James", true, -12.11),
            new Person("Timmy", "Gordon", false, 45),
            new Person("Aiden", "Simpson", false, 0),
            new Person("Jacob", "Grant", true, -92.21),
            new Person("Jackson", "Matthews", false, 0),
            new Person("Ethan", "Beck", false, 48.12),
            new Person("Sophia", "Potts", true, 38.22),
            new Person("Isabella", "Bair", false, 823.43),
            new Person("Olivia", "Fowler", false, -201.23),
            new Person("Jayden", "Walker", true, 49.54),
            new Person("Emma", "Wong", false, -3.49),
            new Person("Chloe", "Samuelsson", false, 0.76),
            new Person("Logan", "Grieve", true, 49.22),
            new Person("Caden", "Sato", false, 90.56),
            new Person("Lilly", "Chin", false, -0.06),
            new Person("Madison", "Barbashov", true, 89.76),
            new Person("Ryan", "Beatty", false, 123.50),
            new Person("Hailey", "Giles", false, 90.56),
            new Person("Molly", "Vos", true, -87.12),
            new Person("Nolan", "Antonio", false, 992.12),
            new Person("Bryce", "Marinacci", false, 1832.29),
            new Person("Maria", "Mayhew", true, -782.12),
            new Person("Lauren", "Holt", false, 291.21),
            
            new Person("Jenny", "Bond", false, 23.64),
            new Person("Billy", "James", true, -12.11),
            new Person("Timmy", "Gordon", false, 45),
            new Person("Aiden", "Simpson", false, 0),
            new Person("Jacob", "Grant", true, -92.21),
            new Person("Jackson", "Matthews", false, 0),
            new Person("Ethan", "Beck", false, 48.12),
            new Person("Sophia", "Potts", true, 38.22),
            new Person("Isabella", "Bair", false, 823.43),
            new Person("Olivia", "Fowler", false, -201.23),
            new Person("Jayden", "Walker", true, 49.54),
            new Person("Emma", "Wong", false, -3.49),
            new Person("Chloe", "Samuelsson", false, 0.76),
            new Person("Logan", "Grieve", true, 49.22),
            new Person("Caden", "Sato", false, 90.56),
            new Person("Lilly", "Chin", false, -0.06),
            new Person("Madison", "Barbashov", true, 89.76),
            new Person("Ryan", "Beatty", false, 123.50),
            new Person("Hailey", "Giles", false, 90.56),
            new Person("Molly", "Vos", true, -87.12),
            new Person("Nolan", "Antonio", false, 992.12),
            new Person("Bryce", "Marinacci", false, 1832.29),
            new Person("Maria", "Mayhew", true, -782.12),
            new Person("Lauren", "Holt", false, 291.21)
        );
    }
    
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String TELECOMMUTER = "telecommuter";
    public static final String BALANCE = "balance";
    
 
    
    public static ObservableList<Map> getTestMap(boolean getProperty) {
        List<Person> personList = getTestList();
        ObservableList<Map> personMapList = FXCollections.observableArrayList();
        
        for (int i = 0; i < personList.size(); i++) {
            Person p = personList.get(i);
            
            Map<String, Object> map = new HashMap<String, Object>();
            
            if (getProperty) {
                map.put(FIRST_NAME, p.firstNameProperty());
                map.put(LAST_NAME, p.lastNameProperty());
                map.put(TELECOMMUTER, p.telecommuterProperty());
                map.put(BALANCE, p.balanceProperty());
            } else {
                map.put(FIRST_NAME, p.getFirstName());
                map.put(LAST_NAME, p.getLastName());
                map.put(TELECOMMUTER, p.isTelecommuter());
                map.put(BALANCE, p.getBalance());
            }
            personMapList.add(map);
        }
        
        return personMapList;
    }
    
    private static Thread liveUpdateThread;
    
    private  long userID;
    
    public long getUserID() {
        return userID;
    }
    
    @XmlElement(name="userId")
    public void setUserId(long v) {
        this.userID = v;
    }
    
    public Person() {
        this.userID = (long) (10000 * Math.random());
        this.firstNameProperty = new SimpleStringProperty(this, "firstName");
        this.lastNameProperty = new SimpleStringProperty(this, "lastName");
        this.telecommuterProperty = new SimpleBooleanProperty(this, "telecommuter");
        this.balanceProperty = new SimpleDoubleProperty(this, "balance");
        this.progressProperty = new SimpleDoubleProperty(this, "progress");
        this.earningsProperty = new SimpleDoubleProperty(this, "earnings");
        this.totalEarningsProperty = new SimpleDoubleProperty(this, "totalEarnings");
        this.totalSalesProperty = new SimpleDoubleProperty(this, "totalSales");
    }
    
    private final StringProperty firstNameProperty;
    public final StringProperty firstNameProperty() { return firstNameProperty; }
    public String getFirstName() { return firstNameProperty.get(); }
    public void setFirstName(String newName) { firstNameProperty.set(newName); }
    
    private final StringProperty lastNameProperty;
    public final StringProperty lastNameProperty() { return lastNameProperty; }
    public String getLastName() { return lastNameProperty.get(); }
    public void setLastName(String newName) { lastNameProperty.set(newName); }
    
    private final BooleanProperty telecommuterProperty;
    public final BooleanProperty telecommuterProperty() { return telecommuterProperty; }
    public boolean isTelecommuter() { return telecommuterProperty.get(); }
    public void setTelecommuter (boolean v) {telecommuterProperty().set(v);    }
    
    private final DoubleProperty balanceProperty;
    public final DoubleProperty balanceProperty() { return balanceProperty; }
    public double getBalance() { return balanceProperty.get(); }
    
    private final DoubleProperty progressProperty;
    public final DoubleProperty progressProperty() { return progressProperty; }
    public double getProgress() { return progressProperty.get(); }
    private void setProgress(double progress) { progressProperty.set(progress); }
    
    private final DoubleProperty earningsProperty;
    public final DoubleProperty earningsProperty() { return earningsProperty; }
    public double getEarnings() { return earningsProperty.get(); }
    public void setEarnings(double earnings) { earningsProperty.set(earnings); }
    
    private final ObservableList<Double> historicEarnings = FXCollections.observableArrayList(new LinkedList<Double>());
    private final ObservableList<Double> unmodifiableHistoricEarnings = FXCollections.unmodifiableObservableList(historicEarnings);
    @ReturnsUnmodifiableCollection public final ObservableList<Double> getHistoricEarnings() { return unmodifiableHistoricEarnings; }
    
    private final DoubleProperty totalEarningsProperty;
    public final DoubleProperty totalEarningsProperty() { return totalEarningsProperty; }
    public double getTotalEarnings() { return totalEarningsProperty.get(); }
    private void setTotalEarnings(double earnings) { totalEarningsProperty.set(earnings); }
    
    private final DoubleProperty totalSalesProperty;
    public final DoubleProperty totalSalesProperty() { return totalSalesProperty; }
    public double getTotalSales() { return totalSalesProperty.get(); }
    private void setTotalSales(double earnings) { totalSalesProperty.set(earnings); }
    
    public Person(String firstName, String lastName, boolean telecommuter) {
        this(firstName, lastName, telecommuter, 0.0);
    }
    
    public Person(String firstName, String lastName, boolean telecommuter, double balance) {
        this.userID = userIDCounter++;
        this.firstNameProperty = new SimpleStringProperty(this, "firstName", firstName);
        this.lastNameProperty = new SimpleStringProperty(this, "lastName", lastName);
        this.telecommuterProperty = new SimpleBooleanProperty(this, "telecommuter", telecommuter);
        this.balanceProperty = new SimpleDoubleProperty(this, "balance", balance);
        this.progressProperty = new SimpleDoubleProperty(this, "progress");
        this.earningsProperty = new SimpleDoubleProperty(this, "earnings");
        this.totalEarningsProperty = new SimpleDoubleProperty(this, "totalEarnings");
        this.totalSalesProperty = new SimpleDoubleProperty(this, "totalSales");

        setProgress(Math.random());
        
        double earnings = Math.random() * 10 * (new Random().nextBoolean() ? 1 : -1);
        setEarnings(earnings);

        double sales = (int) ((Math.random() * 900) + 100) / 10.0;
        setTotalSales(sales);
        
        this.earningsProperty.addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                // add to historic earnings
                if (historicEarnings.size() > MAX_HISTORY) {
                    historicEarnings.remove(0);
                }
                historicEarnings.add((Double)newValue);
                setTotalEarnings(getTotalEarnings() + (Double) newValue);
            }
        });
        
        if (DEBUG) {
            this.firstNameProperty.addListener(new ChangeListener<String>() {
                @Override public void changed(javafx.beans.value.ObservableValue<? extends String> arg0, String ov, String nv) {
                    System.out.println(ov + " is renamed to : " + nv);
                };
            });
            
            this.telecommuterProperty.addListener(new ChangeListener<Boolean>() {
                @Override public void changed(javafx.beans.value.ObservableValue<? extends Boolean> arg0, Boolean ov, Boolean nv) {
                    System.out.println(getFirstName() + " is a remote worker: " + nv);
                };
            });
            
        }
    }
    
    public static void setLiveUpdate(boolean liveUpdate) {
        Person.liveUpdate = liveUpdate;
        
        if (liveUpdate) {
            startLiveUpdateThread();
        }
    }
    
    private static void startLiveUpdateThread() {
        liveUpdateThread = new Thread(new Runnable() {
            int sleep = 30;
            Random r = new Random();

            @Override public void run() {
                while (liveUpdate) {
                    for (Person p : getTestList()) {
                        final Person person = p;
                        double newProgress = (p.getProgress() + 0.025 * r.nextDouble()) % 1.0;
                        boolean updateEarnings = newProgress < p.getProgress();
                        
                        p.setProgress(newProgress);

                        if (updateEarnings) {
                            final double earnings = p.getEarnings() + (Math.random() * 1 * (r.nextBoolean() ? 1 : -1));
                            Platform.runLater(new Runnable() {
                                @Override public void run() {
                                    person.setEarnings(earnings);
                                }
                            });
                        }
                    }
                    
                    try { Thread.sleep(sleep); } catch (InterruptedException ex) { }
                }
            }
        });
        liveUpdateThread.setDaemon(true);
        liveUpdateThread.start();
    }
    
    @Override public String toString() { return getFirstName(); }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.userID ^ (this.userID >>> 32));
        return hash;
    }
    
    @Override
    public boolean equals (Object o) {
        boolean answer = false;
        if (o instanceof Person) {
            Person target = (Person)o;
            answer = target.getUserID() == userID;
        }
        return answer;
    }
}