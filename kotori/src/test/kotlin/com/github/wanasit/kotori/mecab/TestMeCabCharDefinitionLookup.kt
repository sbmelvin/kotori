/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.github.wanasit.kotori.mecab

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class TestMeCabCharDefinitionLookup {

    @Test fun testReadingCharDef() {
        val charDefinition = MeCabCharDefinition.readFromLines("""
            DEFAULT         0 1 0  # DEFAULT is a mandatory category!
            KATAKANA        1 1 2  
            SYMBOL	        1 1 0
            OTHER           0 1 0
            

            0xFF10..0xFF19 OTHER SYMBOL
            0x30FC         KATAKANA
        """.lines()
        )

        assertNotNull(charDefinition)

        assertEquals(4, charDefinition.categoryDefinitions.size)
        assertEquals(3, charDefinition.categoryCharCodeRanges.size) // OTHER, SYMBOL, KATAKANA

        assertEquals("DEFAULT", charDefinition.categoryDefinitions[0].categoryName)
        assertEquals("KATAKANA", charDefinition.categoryDefinitions[1].categoryName)
        assertEquals("SYMBOL", charDefinition.categoryDefinitions[2].categoryName)

        assertEquals(true, charDefinition.createCategoryToDefinition()[1]?.invoke)
        assertEquals(true, charDefinition.createCategoryToDefinition()[1]?.group)

        assertEquals(intArrayOf(0).toSet(), charDefinition.createCharToCategoryMapping()[0x0000.toChar()]?.toSet())

        assertEquals(intArrayOf(0).toSet(), charDefinition.createCharToCategoryMapping()[0xFF0F.toChar()]?.toSet())
        assertEquals(intArrayOf(2, 3).toSet(), charDefinition.createCharToCategoryMapping()[0xFF10.toChar()]?.toSet())
        assertEquals(intArrayOf(2, 3).toSet(), charDefinition.createCharToCategoryMapping()[0xFF11.toChar()]?.toSet())

        assertEquals(intArrayOf(2, 3).toSet(), charDefinition.createCharToCategoryMapping()[0xFF19.toChar()]?.toSet())
        assertEquals(intArrayOf(0).toSet(), charDefinition.createCharToCategoryMapping()[0xFF1A.toChar()]?.toSet())

        assertEquals(intArrayOf(0).toSet(), charDefinition.createCharToCategoryMapping()[0x30FB.toChar()]?.toSet())
        assertEquals(intArrayOf(1).toSet(), charDefinition.createCharToCategoryMapping()[0x30FC.toChar()]?.toSet())
        assertEquals(intArrayOf(0).toSet(), charDefinition.createCharToCategoryMapping()[0x30FD.toChar()]?.toSet())
    }

    @Test fun testReadingCharDefWithOverlapAssign() {
        val charDefinition = MeCabCharDefinition.readFromLines("""
            DEFAULT         0 1 0  
            SYMBOL	        1 1 0
            0xFF10..0xFF19 SYMBOL
            
            # Re-define the value
            OTHER           0 1 0
            0xFF10..0xFF19 OTHER SYMBOL
        """.lines()
        )

        assertNotNull(charDefinition)

        assertEquals(3, charDefinition.categoryDefinitions.size)

        assertEquals(intArrayOf(1, 2).toSet(), charDefinition.createCharToCategoryMapping()[0xFF19.toChar()]?.toSet())
    }
}



