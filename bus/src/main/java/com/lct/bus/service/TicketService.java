package com.lct.bus.service;

import com.lct.bus.dto.TicketDTO;
import com.lct.bus.models.Ticket;
import com.lct.bus.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(int id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public void createTicket(TicketDTO TicketDTO) {
        Boolean existsTicket = ticketRepository.existsById(TicketDTO.getId());
        if (existsTicket) {
            new RuntimeException("Ticket đã tồn tại");
        }

        Ticket t = new Ticket();

        t.setId(TicketDTO.getId());
        t.setUser(TicketDTO.getUser());
        t.setRoute(TicketDTO.getRoute());
        t.setPrice(TicketDTO.getPrice());
        t.setCreatedDate(LocalDateTime.now());
        t.setActive(true);
        ticketRepository.save(t);
    }

    public void updateTicket(Ticket ticket) {
        Ticket ticketUpdate = ticketRepository.findById(ticket.getId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticketUpdate.setUser(ticket.getUser());
        ticketUpdate.setRoute(ticket.getRoute());
        ticketUpdate.setPrice(ticket.getPrice());
        ticketUpdate.setActive(ticket.getActive());

        ticketRepository.save(ticketUpdate);
    }

    public void deleteTicket(int id) {
        ticketRepository.deleteById(id);
    }
}
