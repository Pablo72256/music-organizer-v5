import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;
    // Boleano para saber si se estan reproduciendo canciones
    private boolean isPlaying;

    /**
     * Create a MusicOrganizer
     */
    public MusicOrganizer(String carpeta)
    {
        tracks = new ArrayList<Track>();
        player = new MusicPlayer();
        reader = new TrackReader();
        isPlaying = false;
        readLibrary(carpeta);
        System.out.println("Music library loaded. " + getNumberOfTracks() + " tracks.");
        System.out.println();
    }
    
    /**
     * Add a track file to the collection.
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }
    
    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }
    
    /**
     * Enumera todas las pistas que contengan una cadena buscada
     * @param searchString 
     */
    public void findInTitle(String searchString){
        for (Track track : tracks){
            String title = track.getTitle();
            if (title.contains(searchString)){
                System.out.println(track.getDetails());
            }
        }
    }
    
    public void listAllTrackWithIterator() {
        Iterator<Track> it = tracks.iterator();
        while (it.hasNext()){
            Track trackActual = it.next();
            System.out.println(trackActual.getDetails());
        }
    }
    
    public void removeByArtist(String artistaEliminar) {
        Iterator<Track> it = tracks.iterator();
        while (it.hasNext()){
            Track trackActual = it.next();
            String artist = trackActual.getArtist();
            if(artist.contains(artistaEliminar)) {
                it.remove();
            }
        }
    }
    
    public void removeByTitle(String tituloEliminar) {
        Iterator<Track> it = tracks.iterator();
        while (it.hasNext()){
            Track trackActual = it.next();
            String artist = trackActual.getArtist();
            if(artist.contains(tituloEliminar)) {
                it.remove();
            }
        }
    }
    
    /**
     * Play a track in the collection.
     * @param index The index of the track to be played.
     */
    public void playTrack(int index)
    {
        if(isPlaying == false){
            if(indexValid(index)) {
                Track track = tracks.get(index);
                player.startPlaying(track.getFilename());
                track.incrementPlayCount();
                isPlaying = true;
                System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
            }
        }else{
            System.out.println("Error, reproduccion en curso");
        }
    }
    
    /**
     * Play a random track in the collection.
     * @param index The index of the track to be played.
     */
    public void playRandom()
    {
        Random aleatorio = new Random();
        int numAleatorio = aleatorio.nextInt(tracks.size());
        if(isPlaying == false){
            if(indexValid(numAleatorio)) {
                Track track = tracks.get(numAleatorio);
                player.startPlaying(track.getFilename());
                track.incrementPlayCount();
                isPlaying = true;
                System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
            }
        }else{
            System.out.println("Error, reproduccion en curso");
        }
    }
    
    /**
     * Play a Shuffle track in the collection.
     * @param index The index of the track to be played.
     */
    public void playShuffle()
    {
        ArrayList<Track> copiaTrack = new ArrayList(); 
        copiaTrack = (ArrayList)tracks.clone();
        Random aleatorio = new Random();
        while (copiaTrack.size() > 0){
            int numAleatorio = aleatorio.nextInt(copiaTrack.size());
            if(indexValid(numAleatorio)) {
                Track track = copiaTrack.get(numAleatorio);
                player.playSample(track.getFilename());
                track.incrementPlayCount();
                System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
                copiaTrack.remove(numAleatorio);
            }
        }
    }
    
    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }
    
    /**
     * Devuelve si o no a si se estan reproduciendo tracks.
     * @return The number of tracks in the collection.
     */
    public void isPlaying()
    {
        if(isPlaying == true){
           System.out.println("Se esta reproduciendo"); 
        }else{
           System.out.println("No se esta reproduciendo");
        }
    }
    
    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        System.out.print("Track " + index + ": ");
        Track track = tracks.get(index);
        System.out.println(track.getDetails());
    }
    
    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

        for(Track track : tracks) {
            System.out.println(track.getDetails());
        }
        System.out.println();
    }
    
    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }
    
    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(indexValid(index)) {
            tracks.remove(index);
        }
    }
    
    /**
     * Cambia el a�o de un determinado track
     * 
     */
    public void cambioAnioTrack(int trackSeleccionado, int anioNuevo)
    {
        if(trackSeleccionado >= 0 && trackSeleccionado < tracks.size()){
            tracks.get(trackSeleccionado).modificarAnioActual(anioNuevo);
        }
    }
    
    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if(isPlaying == false){
            if(tracks.size() > 0) {
                Track track = tracks.get(0);
                track.incrementPlayCount();
                player.startPlaying(tracks.get(0).getFilename());
                isPlaying = true;
            }
        }else{
            System.out.println("Error, reproduccion en curso");
        }
    }
    
    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stop();
        isPlaying = false;
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;
        
        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }
    
    private void readLibrary(String folderName)
    {
        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all thetracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }
}
