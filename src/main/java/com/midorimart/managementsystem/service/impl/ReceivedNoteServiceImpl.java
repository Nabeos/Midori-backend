package com.midorimart.managementsystem.service.impl;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.repository.ReceivedNoteRepository;
import com.midorimart.managementsystem.service.ReceivedNoteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceivedNoteServiceImpl implements ReceivedNoteService{
    private final ReceivedNoteRepository receivedNoteRepository;
}
