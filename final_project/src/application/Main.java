package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    // --- Player State Variables ---
    private MediaPlayer mediaPlayer;
    private ObservableList<File> allSongs = FXCollections.observableArrayList();
    private int currentSongIndex = -1;
    private boolean isPlaying = false;
    private boolean isShuffled = false;
    private boolean isRepeat = false;
    private Random random = new Random();

    // --- UI Components ---
    private ImageView playPauseIconView;
    private Image playIcon;
    private Image pauseIcon;
    private Slider progressSlider;
    private Label songTitleLabel;
    private Label artistNameLabel;
    private ListView<File> playlistView;
    private Button repeatBtn; 
    private Label currentTimeLabel;
    private Label totalDurationLabel;

    // --- Canvas for Visualizer ---
    private Canvas visualizerCanvas;
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        playIcon = new Image(getClass().getResource("/images/play.png").toExternalForm());
        pauseIcon = new Image(getClass().getResource("/images/pause.png").toExternalForm());
        
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");

        VBox playerSection = new VBox(15);
        playerSection.setPadding(new Insets(40));
        playerSection.setAlignment(Pos.CENTER);
        playerSection.setMinWidth(400);

        visualizerCanvas = new Canvas(300, 300);
        gc = visualizerCanvas.getGraphicsContext2D();
        drawPlaceholderVisualizer();

        VBox songInfo = new VBox(5);
        songInfo.setAlignment(Pos.CENTER);
        songTitleLabel = new Label("Song Name");
        songTitleLabel.getStyleClass().add("song-title");
        artistNameLabel = new Label("Artist Name");
        artistNameLabel.getStyleClass().add("artist-name");
        songInfo.getChildren().addAll(songTitleLabel, artistNameLabel);

        progressSlider = new Slider();
        HBox.setHgrow(progressSlider, Priority.ALWAYS);

        currentTimeLabel = new Label("0:00");
        currentTimeLabel.getStyleClass().add("time-label");
        totalDurationLabel = new Label("0:00");
        totalDurationLabel.getStyleClass().add("time-label");
        
        HBox progressContainer = new HBox(10, currentTimeLabel, progressSlider, totalDurationLabel);
        progressContainer.setAlignment(Pos.CENTER);

        HBox controls = new HBox(20);
        controls.setAlignment(Pos.CENTER);
        Button shuffleBtn = createIconButton("/images/shuffle.png", 18);
        Button prevBtn = createIconButton("/images/previous.png", 18);
        
        playPauseIconView = new ImageView(playIcon);
        playPauseIconView.setFitWidth(25);
        playPauseIconView.setFitHeight(25);
        Button playPauseBtn = new Button();
        playPauseBtn.setGraphic(playPauseIconView);
        playPauseBtn.setId("play-pause-btn");
        playPauseBtn.getStyleClass().add("icon-button");
        
        Button nextBtn = createIconButton("/images/next.png", 18);
        repeatBtn = createIconButton("/images/repeat.png", 18);
        controls.getChildren().addAll(shuffleBtn, prevBtn, playPauseBtn, nextBtn, repeatBtn);

        playerSection.getChildren().addAll(visualizerCanvas, songInfo, progressContainer, controls);

        VBox rightSection = new VBox(10);
        rightSection.setPadding(new Insets(20));
        rightSection.setMinWidth(450);
        rightSection.setId("right-panel");

        HBox searchArea = new HBox(5);
        searchArea.setAlignment(Pos.CENTER);
        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        HBox.setHgrow(searchField, Priority.ALWAYS);
        Button searchBtn = createIconButton("/images/search.png", 18);
        searchArea.getChildren().addAll(searchField, searchBtn);

        Label queueLabel = new Label("Up Next");
        queueLabel.getStyleClass().add("header-label");
        queueLabel.setPadding(new Insets(10, 0, 0, 0));

        playlistView = new ListView<>();
        playlistView.setItems(allSongs);
        VBox.setVgrow(playlistView, Priority.ALWAYS);

        playlistView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });
        
        Button addSongBtn = createIconButton("/images/add.png", 20);
        
        playlistView.getSelectionModel().selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            if (newIndex != null && newIndex.intValue() != -1) {
                playSong(newIndex.intValue());
            }
        });

        // Data validation
        addSongBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Music Files");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.m4a", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
            
            if (selectedFiles != null) {
                List<File> validSongs = new ArrayList<>();
                List<String> invalidFileNames = new ArrayList<>();

                for (File file : selectedFiles) {
                    try {
                        new Media(file.toURI().toString());
                        validSongs.add(file); 
                    } catch (Exception e) {
                        invalidFileNames.add(file.getName());
                        System.out.println("Could not load file (not a valid media type): " + file.getName());
                    }
                }

                allSongs.addAll(validSongs);
           
                if (!invalidFileNames.isEmpty()) {
                    String message = "The following files could not be loaded as they are not valid media types:\n\n" +
                                     String.join("\n", invalidFileNames);
                    
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Unsupported Files");
                    alert.setHeaderText("Some files were not added to the playlist.");
                    alert.setContentText(message);
                    alert.showAndWait();
                }
            }
        });
        
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String searchText = searchField.getText().toLowerCase().trim();
                List<File> filteredList = allSongs.stream()
                    .filter(file -> file.getName().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());
                playlistView.setItems(FXCollections.observableArrayList(filteredList));
            }
        });
        
        searchField.textProperty().addListener((obs, old, aNew) -> {
            if (aNew == null || aNew.isEmpty()) {
                playlistView.setItems(allSongs);
            }
        });
        
        playPauseBtn.setOnAction(event -> togglePlayPause());
        nextBtn.setOnAction(event -> playNextSong());
        prevBtn.setOnAction(event -> playPreviousSong());
        
        shuffleBtn.setOnAction(event -> {
            isShuffled = !isShuffled;
            shuffleBtn.setStyle(isShuffled ? "-fx-background-color: #D6D6D6;" : "");
        });
        
        repeatBtn.setOnAction(event -> {
            isRepeat = !isRepeat;
            updateRepeatButtonStyle();
        });
        
        progressSlider.setOnMousePressed(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        });

        progressSlider.setOnMouseReleased(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
                if (isPlaying) {
                    mediaPlayer.play();
                }
            }
        });
        
        HBox addContainer = new HBox(addSongBtn);
        addContainer.setAlignment(Pos.CENTER_RIGHT);
        
        rightSection.getChildren().addAll(searchArea, queueLabel, playlistView, addContainer);

        root.setCenter(playerSection);
        root.setRight(rightSection);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);

        primaryStage.setTitle("Music Player");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/logo.png").toExternalForm()));
        primaryStage.show();
    }
    
    private String formatTime(Duration time) {
        int totalSeconds = (int) time.toSeconds();
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
    
    private void updateRepeatButtonStyle() {
        if (repeatBtn != null) {
            repeatBtn.setStyle(isRepeat ? "-fx-background-color: #D6D6D6;" : "");
        }
    }

    private void drawPlaceholderVisualizer() {
        if (gc == null) return;
        gc.clearRect(0, 0, visualizerCanvas.getWidth(), visualizerCanvas.getHeight());
        gc.setFill(Color.web("#E0E0E0"));
        double barWidth = visualizerCanvas.getWidth() / 128;
        for (int i = 0; i < 128; i++) {
            double barHeight = 2;
            double x = i * barWidth;
            double y = visualizerCanvas.getHeight() - barHeight;
            gc.fillRect(x, y, barWidth, barHeight);
        }
    }

    private void setupAudioVisualizer() {
        if (mediaPlayer == null) return;
        mediaPlayer.setAudioSpectrumNumBands(15);
        mediaPlayer.setAudioSpectrumInterval(0.05);

        Stop[] stops = new Stop[] { 
            new Stop(0, Color.web("#007AFF")),
            new Stop(1, Color.web("#34C759"))
        };
        LinearGradient gradient = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);

        mediaPlayer.setAudioSpectrumListener((timestamp, duration, magnitudes, phases) -> {
            gc.clearRect(0, 0, visualizerCanvas.getWidth(), visualizerCanvas.getHeight());
            gc.setFill(gradient);
            double gap = 2;
            double slotWidth = visualizerCanvas.getWidth() / magnitudes.length;
            double barWidth = slotWidth - gap;
            if (barWidth < 1) barWidth = 1;
            
            for (int i = 0; i < magnitudes.length; i++) {
                double dbValue = magnitudes[i] + 60;
                if (dbValue < 0) dbValue = 0;
                double barHeight = dbValue * (visualizerCanvas.getHeight() / 60);
                double x = i * slotWidth + (gap / 2);
                double y = visualizerCanvas.getHeight() - barHeight;
                gc.fillRoundRect(x, y, barWidth, barHeight, 10, 10);
            }
        });
    }

    private void playSong(int songIndex) {
        if (songIndex < 0 || songIndex >= allSongs.size()) {
            return;
        }
        
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        currentSongIndex = songIndex;
        File songFile = allSongs.get(currentSongIndex);
        
        try {
            AudioFile audioFile = AudioFileIO.read(songFile);
            Tag tag = audioFile.getTag();
            String artist = tag.getFirst(FieldKey.ARTIST);
            String title = tag.getFirst(FieldKey.TITLE);
            songTitleLabel.setText(title != null && !title.isEmpty() ? title : songFile.getName().replace(".mp3", ""));
            artistNameLabel.setText(artist != null && !artist.isEmpty() ? artist : "Unknown Artist");
        } catch (Exception e) {
            songTitleLabel.setText(songFile.getName().replace(".mp3", ""));
            artistNameLabel.setText("Unknown Artist");
            System.out.println("Error reading metadata: " + e.getMessage());
        }

        Media media = new Media(songFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        
        playlistView.getSelectionModel().select(currentSongIndex);
        
        setupAudioVisualizer();

        mediaPlayer.setOnReady(() -> {
            progressSlider.setMax(media.getDuration().toSeconds());
            progressSlider.setValue(0);
            totalDurationLabel.setText(formatTime(media.getDuration()));
        });
        
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            Platform.runLater(() -> {
                if (!progressSlider.isValueChanging()) {
                    progressSlider.setValue(newTime.toSeconds());
                }
                currentTimeLabel.setText(formatTime(newTime));
            });
        });
        
        mediaPlayer.setOnEndOfMedia(() -> {
            if (isRepeat) {
                isRepeat = false;
                Platform.runLater(this::updateRepeatButtonStyle);
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            } else {
                playNextSong();
            }
        });

        mediaPlayer.play();
        isPlaying = true;
        playPauseIconView.setImage(pauseIcon);
    }
    
    private void togglePlayPause() {
        if (mediaPlayer == null) {
            if (!allSongs.isEmpty()) {
                playSong(0);
            }
            return;
        }
        
        if (isPlaying) {
            mediaPlayer.pause();
            playPauseIconView.setImage(playIcon);
        } else {
            mediaPlayer.play();
            playPauseIconView.setImage(pauseIcon);
        }
        isPlaying = !isPlaying;
    }

    private void playNextSong() {
        if (allSongs.isEmpty()) {
            drawPlaceholderVisualizer();
            currentTimeLabel.setText("0:00");
            totalDurationLabel.setText("0:00");
            return;
        }

        int nextIndex;

        if (isShuffled) {
            if (allSongs.size() == 1) {
                nextIndex = 0;
            } else {
                do {
                    nextIndex = random.nextInt(allSongs.size());
                } while (nextIndex == currentSongIndex);
            }
        } else {
            nextIndex = currentSongIndex + 1;
            if (nextIndex >= allSongs.size()) {
                if (mediaPlayer != null) mediaPlayer.stop();
                isPlaying = false;
                playPauseIconView.setImage(playIcon);
                songTitleLabel.setText("Song Name");
                artistNameLabel.setText("Artist Name");
                progressSlider.setValue(0);
                drawPlaceholderVisualizer();
                currentTimeLabel.setText("0:00");
                totalDurationLabel.setText("0:00");
                return;
            }
        }
        playSong(nextIndex);
    }

    private void playPreviousSong() {
        if (allSongs.isEmpty()) return;

        currentSongIndex--;
        if (currentSongIndex < 0) {
            currentSongIndex = allSongs.size() - 1;
        }
        playSong(currentSongIndex);
    }

    private Button createIconButton(String imagePath, int size) {
        ImageView iconView = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        iconView.setFitWidth(size);
        iconView.setFitHeight(size);
        iconView.setPreserveRatio(true);
        Button button = new Button();
        button.setGraphic(iconView);
        button.getStyleClass().add("icon-button");
        return button;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}