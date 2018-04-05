package application.demo.rest;

import application.demo.domain.Question;
import application.demo.domain.Variant;
import application.demo.service.QuestionDbService;
import application.demo.service.QuestionService;
import application.demo.service.VariantDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@RestController
@RequestMapping("/variant")
public class VariantRestController {

    @Autowired
    VariantDbService vs;

    @Autowired
    QuestionDbService qs;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Variant>> getAllVariants() {
        ArrayList<Variant> result = (ArrayList<Variant>) vs.findAll();

        if (result.isEmpty()) {
            return new ResponseEntity<ArrayList<Variant>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<ArrayList<Variant>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Variant getVariantById( @PathVariable("id") long id ) {
        return vs.findOne(id);
    }

    @RequestMapping(value = "/quiz/{id}", method = RequestMethod.GET)
    public ArrayList<Variant> getVariantsByQuiz(@PathVariable("id") long id) {
        Question question = QuestionService.getQuizById(id);

        return (ArrayList<Variant>) vs.findByQuestion(question);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Void> addVariant(@RequestBody Variant variant, UriComponentsBuilder ucBuilder ) {
        vs.save(variant);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("variant/{id}").buildAndExpand(variant.getId()).toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
