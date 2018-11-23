package sample;


import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Month;
import java.time.DayOfWeek;
import java.time.MonthDay;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
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
  @FXML
  public Label sunday;
  @FXML
  public Label monday;
  @FXML
  public Label tuesday;
  @FXML
  public Label wednesday;
  @FXML
  public Label thursday;
  @FXML
  public Label friday;
  @FXML
  public Label saturday;

  private LocalDate today;
  private LocalDate date;
  private int daysInAMonth;
  private Month currentMonth;
  private int currentYear;
  private int currentDay;
  private int dayAsNumber;


  private Map<String, String> gameDataMap = new HashMap<>();

  private FileReader fReader;
  private BufferedReader bReader;
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

    today = LocalDate.now();
    date = LocalDate.now();
    setDaysOfTheWeek();
    setYearMonthAndDay();
    gamesToHashMap();
    populateGridPane();

  }

  private void setDaysOfTheWeek() {
    daysOfTheWeek.getChildren().clear();
    sunday.setText(DayOfWeek.SUNDAY.toString());
    monday.setText(DayOfWeek.MONDAY.toString());
    tuesday.setText(DayOfWeek.TUESDAY.toString());
    wednesday.setText(DayOfWeek.WEDNESDAY.toString());
    thursday.setText(DayOfWeek.THURSDAY.toString());
    friday.setText(DayOfWeek.FRIDAY.toString());
    saturday.setText(DayOfWeek.SUNDAY.toString());
    daysOfTheWeek.getChildren().add(sunday);
    daysOfTheWeek.getChildren().add(monday);
    daysOfTheWeek.getChildren().add(tuesday);
    daysOfTheWeek.getChildren().add(wednesday);
    daysOfTheWeek.getChildren().add(thursday);
    daysOfTheWeek.getChildren().add(friday);
    daysOfTheWeek.getChildren().add(saturday);
  }

  public void goToNextMonth(MouseEvent mouseEvent) {
    date = date.plusMonths(1);
    setYearMonthAndDay();
    populateGridPane();
  }

  public void goToPrevMonth(MouseEvent mouseEvent) {
    date = date.minusMonths(1);
    setYearMonthAndDay();
    populateGridPane();
  }

  private void setYearMonthAndDay() {
    currentYear = date.getYear();
    currentMonth = date.getMonth();
    currentDay = date.getDayOfMonth();
    daysInAMonth = date.lengthOfMonth();

    yearLabel.setText("" + currentYear);
    monthLabel.setText(currentMonth.toString());
  }

  private void populateGridPane() {
    boolean executed = false;
    int col;
    calendarView.getChildren().clear();
    dayAsNumber = 1;

    for (int row = 0; row < 6; row++) {
      if (!executed) {
        col = date.with(firstDayOfMonth()).getDayOfWeek().getValue() % 7;
        executed = true;
      } else {
        col = 0;
      }
      for (; col < 7; col++) {

        if (dayAsNumber <= daysInAMonth) {

          Pane cell = new Pane();
          cell.setPrefSize(calendarView.getWidth(), calendarView.getPrefHeight());
          cell.setStyle("-fx-border-color: black; "
              + "-fx-border-radius: .2");
          if (dayAsNumber == currentDay && currentMonth == today.getMonth()) {
            cell.setStyle("-fx-border-color: red; "
                + "-fx-border-radius: .2");
          }

          Label day = new Label("" + dayAsNumber++);
          calendarView.add(day, col, row);
          GridPane.setValignment(day, VPos.TOP);

          /*
          final int listenCol = col;
          final int listenRow = row;
          cell.setOnMouseClicked(e ->
              System.out.println(listenRow + ", " + listenCol)
          );
          */
          calendarView.add(cell, col, row);

          if(gameDataMap.containsKey(currentYear + "-" +currentMonth.getValue()+ "-" + dayAsNumber)){
            StringTokenizer st = new StringTokenizer(gameDataMap.get(currentYear + "-" +currentMonth.getValue()+ "-" + dayAsNumber), "|");
            Label gameLabel = new Label(st.nextToken());
            Label scoreLabel = new Label(st.nextToken());
            calendarView.add(gameLabel, col, row);
            GridPane.setValignment(gameLabel, VPos.CENTER);
            GridPane.setHalignment(gameLabel, HPos.CENTER);
            calendarView.add(scoreLabel, col, row);
            GridPane.setValignment(scoreLabel, VPos.BOTTOM);
            GridPane.setHalignment(scoreLabel, HPos.CENTER);
          }

        }
      }
    }
  }

  private void gamesToHashMap() {
    try {
      fReader = new FileReader("Games.txt");
      bReader = new BufferedReader(fReader);

      String key;
      String value = null;
      while ((key = bReader.readLine()) != null) {
        value = bReader.readLine();
        value = value + "|" + bReader.readLine();
        gameDataMap.put(key, value);
      }
    } catch (IOException ex) {
      System.out.println("Error no games");
      ex.printStackTrace();
    }

  }

  /*
  private void checkForGames(int row, int col) {
    String tempString;
    try {
      tempString = bReader.readLine();
      try {
        if (!tempString.matches("^[0-9][0-9][0-9][0-9](-)[0-9][0-9](-)+[0-9]?[0-9]")) {
          //System.out.println("Before :" + tempString);
          bReader.reset();
          tempString = bReader.readLine();
          //System.out.println("after : " + tempString);
        }
      } catch (NullPointerException ex) {
        bReader.close();
        fReader.close();
      }
      //tempString = bReader.readLine();
      boolean found = false;
      //System.out.println(tempString + "||"+ currentYear + "-" + currentMonth.getValue() + "-" + dayAsNumber);
      if ((tempString).equals(currentYear + "-" + currentMonth.getValue() + "-" + dayAsNumber)) {
        //System.out.println(currentYear + "-" + currentMonth.getValue() + "-" + dayAsNumber);
        System.out.println(tempString + " IS READ");
        //System.out.println(bReader.readLine());
        String teamVsTeam = bReader.readLine();
        String gameScore = bReader.readLine();
        gameLabel = new Label(teamVsTeam);
        scoreLabel = new Label(gameScore);
        calendarView.add(gameLabel, col, row);
        GridPane.setValignment(gameLabel, VPos.CENTER);
        GridPane.setHalignment(gameLabel, HPos.CENTER);
        gameLabel.setWrapText(true);
        calendarView.add(scoreLabel, col, row);
        GridPane.setValignment(scoreLabel, VPos.BOTTOM);
        GridPane.setHalignment(scoreLabel, HPos.CENTER);
        scoreLabel.setWrapText(true);
        //System.out.println(bReader.readLine());
        bReader.mark(1000);
      }
    } catch (IOException | NullPointerException ex) {
      tempString = null;
    }
  }
*/
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
}
