package sample;


import java.time.LocalDate;
import java.time.Month;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class Controller {


  public GridPane daysOfTheWeek;
  public Label yearLabel;
  private LocalDate date;
  private int daysInAMonth;
  private Month currentMonth;
  private int currentYear;

  @FXML
  private Button nextMonth;

  @FXML
  public Button prevMonth;

  @FXML
  private GridPane calendarView;

  @FXML
  private Label monthLabel;

  @FXML
  public void initialize() {
    date = LocalDate.now();
    setMonthAndyear();
    populateGridPane();

  }

  public void goToNextMonth(MouseEvent mouseEvent) {
    date = date.plusMonths(1);
    setMonthAndyear();
    populateGridPane();
  }

  public void goToPrevMonth(MouseEvent mouseEvent) {
    date = date.minusMonths(1);
    setMonthAndyear();
    populateGridPane();
  }

  private void setMonthAndyear(){
    currentYear = date.getYear();
    currentMonth = date.getMonth();
    daysInAMonth = date.lengthOfMonth();

    yearLabel.setText("" + currentYear);
    monthLabel.setText(currentMonth.toString());
  }

  private void populateGridPane(){
    calendarView.getChildren().clear();
    int number = 1;
    for(int row = 0; row < 5; row++){
      for (int col = 0; col < 7; col++){
        if (number < daysInAMonth){
          Label day =new Label("" + number++);
          calendarView.add(day, col,row);
          GridPane.setValignment(day, VPos.TOP);
        }
        else{
          Label day =new Label("");
          calendarView.add(day, col,row);
        }
      }
    }
  }

  //files stuff
}
