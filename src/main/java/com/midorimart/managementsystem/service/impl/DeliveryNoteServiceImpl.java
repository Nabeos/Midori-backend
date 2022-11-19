package com.midorimart.managementsystem.service.impl;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.repository.DeliveryNoteRepository;
import com.midorimart.managementsystem.service.DeliveryNoteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryNoteServiceImpl implements DeliveryNoteService{
    private final DeliveryNoteRepository deliveryNoteRepository;
}
