package br.ufrn.imd.controle;

import javafx.scene.Scene;

// metadados de um arquivo .mp3


//import org.jaudiotagger.audio.AudioFile;
//import org.jaudiotagger.audio.AudioFileIO;
//import org.jaudiotagger.audio.exceptions.CannotReadException;
//import org.jaudiotagger.audio.exceptions.CannotWriteException;
//import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
//import org.jaudiotagger.tag.FieldKey;
//import org.jaudiotagger.tag.Tag;
//import org.jaudiotagger.tag.TagException;
//import org.jaudiotagger.tag.id3.ID3v24Tag;

//

// javafx
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListCell;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
// 
import java.net.URL;

// suporte para operações de E/S (entrada/saída)
// não bloqueantes e manipulação de buffers.

import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

// 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

//

import application.Main;

//
import java.io.File;
import java.io.IOException;

import br.ufrn.imd.dao.MusicasDAO;
import br.ufrn.imd.dao.PlaylistsDAO;
import br.ufrn.imd.dao.UsuariosDAO;
import br.ufrn.imd.modelo.Musicas;
import br.ufrn.imd.modelo.Playlists;

//

import br.ufrn.imd.modelo.Tipo;

//

//import org.jaudiotagger.audio.AudioFile;
//import org.jaudiotagger.audio.AudioFileIO;
//import org.jaudiotagger.tag.Tag;


public class ViewPrincipalController implements Initializable{
	
	public static MusicasDAO musicasDAO;
	public static PlaylistsDAO playlistsDAO;
	public static UsuariosDAO usuariosDAO;
	
	public Main main;
	private Stage stage;
	private Timer tempo;
	private TimerTask tarefa;
	
	private boolean running;
	
	@FXML
	private Button BtnPlayPause;
	@FXML 
	private Button addFilesBtn;
	
	@FXML
	private ProgressBar songProgressBar;
	
	@FXML
	private Label lbSong;
	
	@FXML
	private Slider volumeSong;
	
	@FXML
	private ListView<Musicas> lvMusic;
	
	@FXML
	private ListView<Playlists> lvPlaylist;
	
	@FXML
	private ListView<File>lvRealPlaylist;
	
	private ObservableList<File> musicas;
	
	//private String path;
	private boolean isPlay; 
	//private ArrayList<File> songs;
	private HashSet<File> playlistSet;
	private File directory;
	private File [ ] files;
	private int songNumber;
	private Media media;
	private MediaPlayer mediaPlayer;
	private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		usuariosDAO = UsuariosDAO.getInstance();
		
		Tipo tipo = Tipo.USER_COMUM; //= usuario.getTipo();
		
		// Configurar o comportamento com base no tipo de usuário
        switch (tipo) {
            
            case USER_COMUM:
              
            	// Configuração específica para usuários comuns
            	//addFilesBtn.setDisable(true); // Desabilita o botão para usuários comuns
                break;
                
            case USER_VIP:
                
            	// Configuração específica para usuários VIP
            	//...
                break;
        }
        
		System.out.println("Inicializei o controle da tela principal -- " + System.getProperty("user.dir"));
		//path = System.getProperty("user.dir");
		
		// PEGA A INSTANCIA DE Playlist
		playlistsDAO = PlaylistsDAO.getInstance();
		
		
		
		
		// PEGA A INSTANCIA DE MUSICAS
		musicasDAO = MusicasDAO.getInstance();  
		
		//songs = new ArrayList<File>();
		
		lvPlaylist.getItems().addAll(playlistsDAO.getPlaylist());
		
		playlistSet = new HashSet<>();
		// C:\Users\Diniz\eclipse-workspace\TrabalhoFinalLP2 == System.getProperty("user.dir")
		directory = new File(System.getProperty("user.dir") + "\\data\\musicas");
		//directory = new File(System.getProperty("user.dir") , "data\\musicas");
		//directory = new File("C:\\Users\\Diniz\\Desktop\\Java\\Novapasta33\\Musics");
		
		// só quem pode criar diretorios é um usuario admin / vip
		//System.out.println("a");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		
		
		files = directory.listFiles();
		
		//files[0].listFiles();
		if( files != null)
		{
			for(File file : files)
			{
				try {
					//if (AudioFileIO.isAudioFile(file)) {
			            AudioFile audioFile = AudioFileIO.read(file);
			            Tag tag = audioFile.getTag();

			            String nome = tag.getFirst(FieldKey.TITLE);
			            String banda = tag.getFirst(FieldKey.ARTIST);
			            int duracao = audioFile.getAudioHeader().getTrackLength();

			            System.out.println("Nome: " + nome + ", banda: " + banda + ", duracao: " + duracao + ", file: " + file);
			            musicasDAO.adicionarMusica(new Musicas(nome, banda, duracao, file));
			       // } else {
			            //System.out.println("Arquivo não é um arquivo de áudio válido: " + file);
			        //} 
					
				} 
				catch (CannotReadException e) {
			        System.out.println("Não foi possível ler o arquivo como um arquivo de áudio válido: " + file);
			        e.printStackTrace();
				}
				catch (Exception e) {
			        	System.out.println("Erro ao processar o arquivo: " + file);
		            e.printStackTrace();
		        }
				
				//musicasDAO.addSong(file);
				//System.out.println("File: " + file);
			}
		}
		
		//
		songNumber = 0;
		
		// pegando a primeira musica
		if (musicasDAO.IsHaveAnySound()) {		
			media = new Media(musicasDAO.GetSongs().get(songNumber).getArquivo().toURI().toString());
		}
		//.getArquivo().get(songNumber).toURI().toString()
		// colocando no media Player
		if (media != null) {
			mediaPlayer = new MediaPlayer(media);
		}
		
		
		// lbSong é meu label 
		lbSong.setText(musicasDAO.GetSongs().get(0).getArquivo().getName());

		
		
		isPlay = false;
		
		
		
		songProgressBar.setProgress(0);
		
		volumeSong.valueProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
			{
				mediaPlayer.setVolume(volumeSong.getValue() * 0.02);
			}
		});
		
		// Criação das listas de músicas e playlist
		//musicas = FXCollections.observableArrayList();
		
		// lvMusic é minha lista de músicas
		
		//ListView<File> lvMusic = new ListView<>();
		
		lvMusic.getItems().addAll(musicasDAO.GetSongs());
		
		
		 try {
	            //AudioFile audioFile = AudioFileIO.read(arquivo);
	            //Tag tag = audioFile.getTag();

	            //String nome = tag.getFirst(FieldKey.TITLE);
	            //String banda = tag.getFirst(FieldKey.ARTIST);
	            //int duracao = audioFile.getAudioHeader().getTrackLength();

	            //Musica musica = new Musica(nome, banda, duracao, arquivo);
	            //MusicaDAO.adicionarMusica(musica);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		lvPlaylist.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                deleteSelectedMusic();
                event.consume();
            }
        });
		
		
	}
	
	
	@FXML
    public void setOnMouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
            File selectedItemPL = lvPlaylist.getSelectionModel().getSelectedItem();
            File selectedItemMsc = lvMusic.getSelectionModel().getSelectedItem().getArquivo();
            if (selectedItemPL != null) {
            	for (Musicas file : musicasDAO.GetSongs()) {
                    if (file.getArquivo().getName().equals(selectedItemPL.getName())) {
                        media = new Media(file.getArquivo().toURI().toString());
                        break;
                    }
                }
                if (media != null) {
                    // Use a instância de Media como necessário
                	mediaPlayer = new MediaPlayer(media);
                    // ...
                }
                PlayPauseMusic();
            }
            else if(selectedItemMsc != null) {
            	for (Musicas file : musicasDAO.GetSongs()) {
                    if (file.getArquivo().getName().equals(selectedItemMsc.getName())) {
                        media = new Media(file.getArquivo().toURI().toString());
                        break;
                    }
                }
                if (media != null) {
                    // Use a instância de Media como necessário
                	mediaPlayer = new MediaPlayer(media);
                    // ...
                }
                PlayPauseMusic();
            }
            
        }
	}
	
	
	@FXML
    public void onMusicListDragDetected(MouseEvent event) {
        Dragboard dragboard = lvMusic.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        ArrayList<File> selectedItem = new ArrayList<>();
        
        selectedItem.add( lvMusic.getSelectionModel().getSelectedItem().getArquivo());
        
        content.put(SERIALIZED_MIME_TYPE, selectedItem);
        dragboard.setContent(content);
        event.consume();
    }

    @FXML
    public void onPlaylistDragOver(DragEvent event) {
        if (event.getGestureSource() != lvPlaylist && event.getDragboard().hasContent(SERIALIZED_MIME_TYPE)) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    public void onPlaylistDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasContent(SERIALIZED_MIME_TYPE)) {
            ArrayList<File> files = (ArrayList<File>) dragboard.getContent(SERIALIZED_MIME_TYPE);
            for (File file : files) {
                if (!playlistSet.contains(file)) {
                    playlistSet.add(file);
                    lvPlaylist.getItems().add(file);
                }
            }
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }
    
    @FXML
    public void onRealPlaylistDragOver(DragEvent event) {
    	Dragboard dragboard = event.getDragboard();
    	
    	if (dragboard.hasFiles()) {
    		if (event.getGestureSource() != lvRealPlaylist && event.getDragboard().hasContent(SERIALIZED_MIME_TYPE)) {
    			event.acceptTransferModes(TransferMode.MOVE);
    		}
    	}
        event.consume();
    }

    @FXML
    public void onRealPlaylistDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) 
        {
        	if (dragboard.hasContent(SERIALIZED_MIME_TYPE)) 
        	{
        		ArrayList<File> files = (ArrayList<File>) dragboard.getContent(SERIALIZED_MIME_TYPE);
        		for (File file : files) 
        		{
        			if (!playlistSet.contains(file))
        			{
        				playlistSet.add(file);
        				lvRealPlaylist.getItems().add(file);
        			}
        		}
        		success = true;
        	}
        }
        event.setDropCompleted(success);
        event.consume();
    }
    
    private void deleteSelectedMusic() {
        File selectedFile = lvPlaylist.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
        	lvPlaylist.getItems().remove(selectedFile);
            playlistSet.remove(selectedFile);
        }
    }
    
	@FXML
	public void PlayPauseMusic() {
		System.out.println("cliquei para começar a música");
		if (!isPlay) {
			TempoInit();
			mediaPlayer.play();
			isPlay = true;
		}
		else {
			//paraTempo();
			mediaPlayer.pause();
			isPlay = false;
		}
	}
	@FXML
	public void prevSong( )
	{
		if(songNumber > 0)
		{
			songNumber--;
			
			mediaPlayer.stop();
			isPlay = false;
			songProgressBar.setProgress(0);
			media = new Media(musicasDAO.GetSongs().get(songNumber).getArquivo().toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			lbSong.setText(musicasDAO.GetSongs().get(songNumber).getArquivo().getName());
			PlayPauseMusic();
		}else {
			songNumber = musicasDAO.GetSongs().size() - 1;
			
			mediaPlayer.stop();
			isPlay = false;
			
			songProgressBar.setProgress(0);
			
			media = new Media(musicasDAO.GetSongs().get(songNumber).getArquivo().toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			lbSong.setText(musicasDAO.GetSongs().get(songNumber).getArquivo().getName());
			PlayPauseMusic();
		}
	}
	
	@FXML
	public void nextSong( )
	{
		if(songNumber < musicasDAO.GetSongs().size() - 1)
		{
			songNumber++;
			
			mediaPlayer.stop();
			isPlay = false;
			//songProgressBar.setProgress(0);
			media = new Media(musicasDAO.GetSongs().get(songNumber).getArquivo().toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			lbSong.setText(musicasDAO.GetSongs().get(songNumber).getArquivo().getName());
			PlayPauseMusic();
		}else {
			songNumber = 0;
			
			mediaPlayer.stop();
			isPlay = false;
			//songProgressBar.setProgress(0);
			media = new Media(musicasDAO.GetSongs().get(songNumber).getArquivo().toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			lbSong.setText(musicasDAO.GetSongs().get(songNumber).getArquivo().getName());
			PlayPauseMusic();
		}
	}
	
	public void TempoInit()
	{
		tempo = new Timer();
		
		tarefa = new TimerTask()
		{
			public void run()
			{
				running = true;
				double current = mediaPlayer.getCurrentTime().toSeconds();
				double end = media.getDuration().toSeconds();
				//System.out.println(current/end);
				songProgressBar.setProgress(current/end );			
				double marginOfError = 0.003; // Definir uma margem de erro adequada
				if (Math.abs(current / end - 1) < marginOfError) {
				    paraTempo();
				}
			}
		};
		tempo.scheduleAtFixedRate(tarefa, 1000, 1000);
	}
	
	public void paraTempo()
	{
		running = false;
		tempo.cancel();
	}
	
	@FXML
	public void addFPlayList() //throws IOException 
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory;
		selectedDirectory = directoryChooser.showDialog(null);
		
		if(selectedDirectory != null)
		{
			lvRealPlaylist.getItems().add(selectedDirectory);
		}
		
	}
	
	@FXML
	public void addFiles() throws IOException 
	{
		System.out.println("Entrei no método addFiles");
		 Path from;
		 Path to;
		 File selectedFile;
		 CopyOption [] options;
		 
		 FileChooser fc = new FileChooser();
		 fc.setTitle("Attach a file");
		 selectedFile = fc.showOpenDialog(null);
		 
		 // copiando os files para o diretorio x
		 if (selectedFile != null) 
		 {
		    from = Paths.get(selectedFile.toURI());
		    to = Paths.get(System.getProperty("user.dir") + "\\data\\musicas\\" + selectedFile.getName());
		    options = new CopyOption[]{
	                StandardCopyOption.REPLACE_EXISTING,
	                StandardCopyOption.COPY_ATTRIBUTES
		    };
		    Files.copy(from, to, options);
		    
		    try {
	            AudioFile audioFile = AudioFileIO.read(selectedFile);
	            Tag tag = audioFile.getTag();

	            String nome = tag.getFirst(FieldKey.TITLE);
	            String banda = tag.getFirst(FieldKey.ARTIST);
	            int duracao = audioFile.getAudioHeader().getTrackLength();
	            
	            Musicas newMusica = new Musicas(nome, banda, duracao,selectedFile);
	            musicasDAO.adicionarMusica(newMusica); 
	            lvMusic.getItems().add(newMusica);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		    
		    //songs.add(selectedFile);
		    System.out.println("File: " + selectedFile);
		    //lbSong.setText(songs.get(songs.size()-1).getName());
		 }	
		 
		 
			 /*if (file.exists() && file.isFile()) {
		            try {
		                AudioFile audioFile = AudioFileIO.read(file);
		                Tag tag = audioFile.getTag();

		                System.out.println("Detalhes do arquivo MP3:");
		                System.out.println("Título: " + tag.getFirst(FieldKey.TITLE));
		                System.out.println("Artista: " + tag.getFirst(FieldKey.ARTIST));
		                System.out.println("Álbum: " + tag.getFirst(FieldKey.ALBUM));
		                System.out.println("Ano: " + tag.getFirst(FieldKey.YEAR));
		                System.out.println("Gênero: " + tag.getFirst(FieldKey.GENRE));
		                // Outros metadados disponíveis: FieldKey.COMMENT, FieldKey.TRACK, etc.

		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        } else {
		            System.out.println("Arquivo MP3 não encontrado.");
		        }*/
		 
	}
	
	
	
	
	@FXML
	public void GenMusic()
	{
		
	}
	@FXML
	public void NamePlaylist()
	{
		
	}
	
	
	
	public void setPrincipalStage(Stage stage) {
			this.stage = stage;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
}
