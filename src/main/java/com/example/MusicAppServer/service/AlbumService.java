package com.example.MusicAppServer.service;


import com.example.MusicAppServer.model.Album;
import com.example.MusicAppServer.model.Artist;
import com.example.MusicAppServer.model.Track;
import com.example.MusicAppServer.repositories.AlbumRepository;
import com.example.MusicAppServer.repositories.TrackRepository;
import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    private final TrackRepository trackRepository;

    public AlbumService(AlbumRepository albumRepository, TrackRepository trackRepository) {
        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
    }


    public OperationResult createAlbum(Long artistId, Album album, MultipartFile image, List<MultipartFile> audioFiles) {
        try {

            albumRepository.save(album);
            album.getTrackNames();
            FileService fileService = new FileService(image, artistId, audioFiles, album);

            String albumImagePath = fileService.saveAlbumImage();
            album.setImagePath(albumImagePath);
            album.setArtist_id(artistId);

            List<String> tracksPath = fileService.saveAlbumAudioFiles();

            albumRepository.save(album);

            for (int i = 0; i < album.getTrackNames().size(); i++) {
                Track track = new Track();
                System.out.println(album.getTrackNames().get(i));
                System.out.println(tracksPath.get(i));
                track.setName(album.getTrackNames().get(i));
                track.setAudioPath(tracksPath.get(i));
                track.setAlbum(album);
                trackRepository.save(track);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
        return new OperationResult<>(OperationStatus.SUCCESS);
    }

    public OperationResult getAllAlbums()
    {
        try {
            List<Album> albums = albumRepository.findAll();
            if (albums == null)
            {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            return new OperationResult<>(OperationStatus.SUCCESS,albums);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public OperationResult getAllAlbumsByArtistId(Long id)
    {
        try {
            List<Album> albums = albumRepository.findAlbumsByArtist_id(id);
            if (albums == null)
            {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
