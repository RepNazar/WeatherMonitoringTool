package ua.Nazar.Rep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.Nazar.Rep.domain.Record;
import ua.Nazar.Rep.domain.User;
import ua.Nazar.Rep.repos.RecordRepo;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@Controller
public class RecordController {

    private RecordRepo recordRepo;

    @Autowired
    public void setRecordRepo(RecordRepo recordRepo) {
        this.recordRepo = recordRepo;
    }

    @GetMapping("/")
    public String getRecords(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Record> records = recordRepo.findAll();

        if (filter != null && !filter.isEmpty()) {
            records = recordRepo.findAllByDateStartingWith(filter);
        }

        model.addAttribute("records", records);
        model.addAttribute("filter", filter);

        return "records";
    }

    @GetMapping("/user-records/{user}")
    public String userRecords(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Record record,
            @RequestParam(required = false, defaultValue = "") String filter
    ) {
        Iterable<Record> records = user.getRecords();

        if (filter != null && !filter.isEmpty()) {
            records = recordRepo.findAllByDateStartingWithAndAuthor(filter,user);
        }

        model.addAttribute("records", records);
        model.addAttribute("record", record);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("filter", filter);

        return "userRecords";
    }

    @PostMapping("/user-records/{user}")
    public String updateRecord(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            //@RequestParam(required = false, name = "id") Record oldRecord,
            @Valid Record record,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            if(!(record.getId() == null)) {
                Record record2 = recordRepo.findById(record.getId()).orElse(null);
//                model.addAttribute("record", record2);
            }
//            model.addAttribute("record", record);
            Iterable<Record> records = user.getRecords();
            model.addAttribute("records", records);
            model.addAttribute("isCurrentUser", currentUser.equals(user));
            return "userRecords";

        } else {
            record.setAuthor(user);
            model.addAttribute("record", null);
            recordRepo.save(record);

            Iterable<Record> records = user.getRecords();
            model.addAttribute("records", records);
            model.addAttribute("isCurrentUser", currentUser.equals(user));
        }

        return "redirect:/user-records/" + user.getId();

    }

    @GetMapping("/user-records/{user}/delete/{id}")
    public String deleteRecord(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @PathVariable("id") Long id,
            Model model
    ) {
        if (user.equals(currentUser.getId())) {
            recordRepo.deleteById(id);
        }
        Iterable<Record> records = recordRepo.findAll();

        model.addAttribute("records", records);
        return "redirect:/user-records/" + user;
    }

    /*
        @PostMapping("/user-records/{user}")
        public String updateRecord(
                @AuthenticationPrincipal User currentUser,
                @PathVariable Long user,
                @RequestParam(required = false, name = "id") Record record,
                @RequestParam("date") String date,
                @RequestParam("temperature") String temperature,
                @RequestParam("windSpeed") String windSpeed,
                @RequestParam("windAngle") String windAngle,
                @RequestParam("pressure") String pressure
        ) {
            if(record == null){
                record = new Record(date, temperature, windSpeed, windAngle, pressure, currentUser);
            }else {
                if (record.getAuthor().equals(currentUser)) {
                    if (!StringUtils.isEmpty(date)) {
                        record.setDate(date);
                    }

                    if (!StringUtils.isEmpty(temperature)) {
                        record.setTemperature(temperature);
                    }

                    if (!StringUtils.isEmpty(windSpeed)) {
                        record.setWindSpeed(windSpeed);
                    }

                    if (!StringUtils.isEmpty(windAngle)) {
                        record.setWindAngle(windAngle);
                    }

                    if (!StringUtils.isEmpty(pressure)) {
                        record.setPressure(pressure);
                    }
                }
            }
                recordRepo.save(record);

            return "redirect:/user-records/" + user;
        }
    */
    @PostMapping("/")
    public String addRecord(
            @AuthenticationPrincipal User currentUser,
            @Valid Record record,
            BindingResult bindingResult,
            Model model
    ) {
        record.setAuthor(currentUser);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("record", record);
        } else {
            model.addAttribute("record", null);
            recordRepo.save(record);
        }
        Iterable<Record> records = recordRepo.findAll();

        model.addAttribute("records", records);

        return "records";
    }

}