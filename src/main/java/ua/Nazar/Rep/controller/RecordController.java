package ua.Nazar.Rep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import ua.Nazar.Rep.service.RecordService;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RecordController {

    private RecordService recordService;

    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * @param filter
     * @param model
     * @param pageable
     * @return Page with records by filter
     */
    @GetMapping("/")
    public String getRecords(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 25) Pageable pageable
    ) {
        Page<Record> page = recordService.findAll(filter, pageable);

        model.addAttribute("page", page);
        model.addAttribute("filter", filter);

        return "records";
    }

    /**
     * @param currentUser
     * @param user
     * @param model
     * @param record
     * @param filter
     * @param pageable
     * @return Page of user records by filter
     */
    @GetMapping("/user-records/{user}")
    public String userRecords(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Record record,
            @RequestParam(required = false, defaultValue = "") String filter,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 25) Pageable pageable
    ) {
        Page<Record> page = recordService.findAllByAuthor(user, filter, pageable);

        model.addAttribute("page", page);
        model.addAttribute("record", record);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("filter", filter);

        return "userRecords";
    }

    /**
     * @param currentUser
     * @param user
     * @param record
     * @param bindingResult
     * @param model
     * @param pageable
     * @return Redirect on user records or re-enter
     */
    @PostMapping("/user-records/{user}")
    public String commitRecord(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @Valid Record record,
            BindingResult bindingResult,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            Page<Record> page = recordService.findAllByAuthor(user, pageable);

            model.addAttribute("record", record);
            model.addAttribute("page", page);
            model.addAttribute("isCurrentUser", currentUser.equals(user));
            return "userRecords";

        } else {
            record.setAuthor(user);
            model.addAttribute("record", null);
            recordService.save(record);

            Page<Record> page = recordService.findAllByAuthor(user, pageable);

            model.addAttribute("page", page);
            model.addAttribute("isCurrentUser", currentUser.equals(user));
        }

        return "redirect:/user-records/" + user.getId();

    }

    /**
     * @param currentUser
     * @param user
     * @param id
     * @return Redirect on user records
     */
    @PostMapping("/user-records/{user}/delete")
    public String deleteRecord(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam Long id
    ) {
        if (user.equals(currentUser.getId())) {
            recordService.deleteById(id);
        }

        return "redirect:/user-records/" + user;
    }

}
