package com.exed1on.websyshotel.config;

import com.exed1on.websyshotel.entity.Room;
import com.exed1on.websyshotel.entity.User;
import com.exed1on.websyshotel.repository.RoomRepository;
import com.exed1on.websyshotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setEmail("admin@hotel.com");
            user.setEnabled(true);
            userRepository.save(user);

            User user2 = new User();
            user2.setUsername("user");
            user2.setPassword(passwordEncoder.encode("user123"));
            user2.setEmail("user@hotel.com");
            user2.setEnabled(true);
            userRepository.save(user2);
        }

        if (roomRepository.count() == 0) {
            Room auroraRoom = new Room();
            auroraRoom.setName("Aurora Suite");
            auroraRoom.setType("Suite");
            auroraRoom.setPrice(new BigDecimal("299.99"));
            auroraRoom.setAmenities(Arrays.asList("King Bed", "Ocean View", "Jacuzzi", "Mini Bar", "WiFi", "Room Service"));
            auroraRoom.setDescription("Luxurious suite with ocean view and premium amenities");
            roomRepository.save(auroraRoom);

            Room heritageRoom = new Room();
            heritageRoom.setName("Heritage Deluxe");
            heritageRoom.setType("Deluxe");
            heritageRoom.setPrice(new BigDecimal("199.99"));
            heritageRoom.setAmenities(Arrays.asList("Queen Bed", "City View", "Work Desk", "WiFi", "Coffee Maker"));
            heritageRoom.setDescription("Elegant deluxe room with city view and workspace");
            roomRepository.save(heritageRoom);

            Room urbanRoom = new Room();
            urbanRoom.setName("Urban Comfort");
            urbanRoom.setType("Standard");
            urbanRoom.setPrice(new BigDecimal("129.99"));
            urbanRoom.setAmenities(Arrays.asList("Double Bed", "WiFi", "TV", "Air Conditioning"));
            urbanRoom.setDescription("Comfortable standard room with essential amenities");
            roomRepository.save(urbanRoom);
        }
    }
}
