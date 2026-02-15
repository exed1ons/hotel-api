package com.exed1on.websyshotel.service;

import com.exed1on.websyshotel.dto.response.RoomResponse;
import com.exed1on.websyshotel.exception.ResourceNotFoundException;
import com.exed1on.websyshotel.mapper.RoomMapper;
import com.exed1on.websyshotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toResponse)
                .collect(Collectors.toList());
    }

    public RoomResponse findRoomById(Long id) {
        return roomRepository.findById(id)
                .map(roomMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }
}
