package com.iss.workshop2.rng.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.iss.workshop2.rng.exception.RandomNumberException;
import com.iss.workshop2.rng.model.Generate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class generatorController {

    private Logger logger = LoggerFactory.getLogger(generatorController.class);

    @GetMapping("/start")
    public String showGenerateForm(Model model) {
        Generate generate = new Generate();
        model.addAttribute("generateObj", generate);

        return "generatePage";
    }

    
    @PostMapping("/generate")
    public String generateNumber(@ModelAttribute("generate") Generate generate, Model model) {
        try {
            logger.info("From the form " + generate.getNoOfNumberToRandomize());

            int numberRandomNumbers = generate.getNoOfNumberToRandomize();
            if(numberRandomNumbers >= 31 || numberRandomNumbers <= 0) {
                throw new RandomNumberException();
            }

            //Img Array
            String[] images = {"number1.jpg", "number2.jpg", "number3.jpg", "number4.jpg", "number5.jpg", "number6.jpg", "number7.jpg", 
            "number8.jpg", "number9.jpg", "number10.jpg", "number11.jpg", "number12.jpg", "number13.jpg", "number14.jpg", "number15.jpg",
            "number16.jpg", "number17.jpg", "number18.jpg", "number19.jpg", "number20.jpg", "number21.jpg", "numbe22.jpg", "number23.jpg", "number24.jpg",
            "number25.jpg", "number26.jpg", "number27.jpg", "number28.jpg", "number29.jpg", "number30.jpg"};

            List<String> selectedImgs = new ArrayList<String>();

            Random rnd = new Random();

            Set<Integer> uniqueNumbers = new HashSet<Integer>();

            while(uniqueNumbers.size() < numberRandomNumbers) {
                int numberGenerated = rnd.nextInt(30)+1;
                uniqueNumbers.add(numberGenerated);
            }

            for(Integer num : uniqueNumbers) {
                selectedImgs.add("number"+num.toString());
            }

            model.addAttribute("randNumsResult", selectedImgs.toArray());
            model.addAttribute("numInputByUser", numberRandomNumbers);
        } 
        catch(RandomNumberException e) {
            model.addAttribute("errorMsg", "Number enter is less than 0 or more than 30!!");

            return "error";
        }
        return "result";
    }
}
