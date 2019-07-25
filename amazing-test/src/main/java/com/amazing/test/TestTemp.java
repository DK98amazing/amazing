package com.amazing.test;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * TestTemp.
 *
 * @author liguoyao
 */
public class TestTemp {
    public static void main(String[] args) throws IOException, ConfigurationException {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        propertiesConfiguration.read(new InputStreamReader(new FileInputStream(new File("D:\\Application\\VNote\\noteRoot\\ROOT\\new_note_001.md"))));
        System.err.println(propertiesConfiguration.getHeader());
    }

    private static void get() {
        throw new RuntimeException();
    }
}
