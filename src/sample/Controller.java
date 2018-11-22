package sample;


import java.time.LocalDate;
import java.time.Month;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class Controller {

  @FXML
  public GridPane daysOfTheWeek;
  @FXML
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

  private void setMonthAndyear() {
    currentYear = date.getYear();
    currentMonth = date.getMonth();
    daysInAMonth = date.lengthOfMonth();

    yearLabel.setText("" + currentYear);
    monthLabel.setText(currentMonth.toString());
  }

  private void populateGridPane() {
    calendarView.getChildren().clear();
    int number = 1;
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 7; col++) {
        Pane cell = new Pane();
        cell.setPrefSize(calendarView.getWidth(), calendarView.getPrefHeight());
        cell.setStyle("-fx-border-color: black; "
            + "-fx-border-radius: .2" );

        if (number <= daysInAMonth) {

          Label day = new Label("" + number++);
          calendarView.add(day, col, row);
          GridPane.setValignment(day, VPos.TOP);
          final int listenCol = col;
          final int listenRow = row;
          cell.setOnMouseClicked(e ->
            System.out.println(listenRow + ", " + listenCol)
          );
          calendarView.add(cell, col, row);
        } else {
          Label day = new Label("");
          calendarView.add(day, col, row);

        }
      }
    }
  }

/*
  private void populateGridPane(){
    calendarView.getChildren().clear();
    int number = 1;
    for(int row = 0; row < 5; row++){
      for (int col = 0; col < 7; col++){
        if (number < daysInAMonth){
          Label day =new Label("" + number++);
          calendarView.add(day, col,row);
          GridPane.setValignment(day, VPos.TOP);
          System.out.println("set " + row + ", " + col);
        }
        else{
          Label day =new Label("");
          calendarView.add(day, col,row);
        }
      }
    }
  }
  */
/*
  public void printData(MouseEvent mouseEvent) {
    Node source = (Node)mouseEvent.getSource();
    Integer colIndex = calendarView.getColumnIndex(source);
    Integer rowIndex = GridPane.getRowIndex(source);
    System.out.println(colIndex);
  }
*/
  //files stuff
}
