package com.novisign.demo.controller;

import static com.novisign.demo.TestData.TEST_ID_1;
import static com.novisign.demo.TestData.TEST_ID_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.novisign.demo.service.ProofOfPlayService;

@ExtendWith(MockitoExtension.class)
public class ProofOfPlayControllerTest {

    @Mock
    private ProofOfPlayService mockProofOfPlayService;
    @InjectMocks
    private ProofOfPlayController unit;

    @Test
    public void proofOfPlayTest() {
        //GIVEN
        doNothing().when(mockProofOfPlayService).proofOfPlay(TEST_ID_1, TEST_ID_2);

        //WHEN
        ResponseEntity<Void> actualResult = unit.proofOfPlay(TEST_ID_1, TEST_ID_2);

        //THEN
        assertEquals(actualResult.getStatusCode(), HttpStatus.OK);

        verify(mockProofOfPlayService).proofOfPlay(TEST_ID_1, TEST_ID_2);
        verifyNoMoreInteractions(mockProofOfPlayService);
    }

}
