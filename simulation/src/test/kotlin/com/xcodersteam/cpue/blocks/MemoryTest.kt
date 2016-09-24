package com.xcodersteam.cpue.blocks

import com.xcodersteam.cpue.Simulation.power
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Created by Semoro on 24.09.16.
 * ©XCodersTeam, 2016
 */
class MemoryTest : AbstractSimulationTest() {


    @Test
    fun testTrivialMemory() {
        val memory = MemoryBlock(1, 1, 0, 2)
        simulateNSteps(10) {
            memory.addressBus.nodes[0].power = true
            memory.dataBus.nodes[0].power = true
            memory.s.power = true
        }
        simulateNSteps(10) {}
        simulateNSteps(10) {
            memory.addressBus.nodes[0].power = true
            memory.s.power = true
        }
        assertTrue(memory.dataBus.nodes[0].power)
    }

    @Test
    fun testMemory() {
        val memory = MemoryBlock(8, 8, 0, 256)
        for (i in 0..255) {
            simulateNSteps(3) {
                memory.addressBus.asBits = i
                memory.dataBus.asBits = i
                memory.s.power = true
            }
            simulateNSteps(2) {}
        }
        simulateNSteps(10) {}
        for (i in 0..255) {
            simulateNSteps(3) {
                memory.addressBus.asBits = i
                memory.s.power = true
            }
            assertEquals(i, memory.dataBus.asBits)
        }

        simulateNSteps(100) {}
        for (i in 0..255) {
            simulateNSteps(1) {
                memory.addressBus.asBits = i
                memory.r.power = true
            }
            simulateNSteps(3) {
                memory.addressBus.asBits = i
                memory.dataBus.asBits = 255 - i
                memory.s.power = true
            }
            simulateNSteps(2) {}
        }
        simulateNSteps(10) {}
        for (i in 0..255) {
            simulateNSteps(3) {
                memory.addressBus.asBits = i
                memory.s.power = true
            }
            assertEquals(255 - i, memory.dataBus.asBits)
        }

    }
}