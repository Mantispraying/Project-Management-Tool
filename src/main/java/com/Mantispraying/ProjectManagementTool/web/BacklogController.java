package com.Mantispraying.ProjectManagementTool.web;

import com.Mantispraying.ProjectManagementTool.domain.ProjectTask;
import com.Mantispraying.ProjectManagementTool.services.MapValidationErrorService;
import com.Mantispraying.ProjectManagementTool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
    @Autowired
    private ProjectTaskService projectTaskService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id,projectTask, principal.getName());

        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public ResponseEntity<Iterable<ProjectTask>> getProjectBacklog(@PathVariable String backlog_id, Principal principal){
        return new ResponseEntity<>(projectTaskService.findBacklogById(backlog_id, principal.getName()),HttpStatus.OK);
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id, principal.getName());
        return new ResponseEntity<>(projectTask,HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        ProjectTask updatedTask = projectTaskService.updateProjectSequence(projectTask, backlog_id, pt_id, principal.getName());

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        projectTaskService.deletePTByProjectSequence(backlog_id, pt_id, principal.getName());
        return new ResponseEntity<>("Project task "+pt_id+" was deleted successfully",HttpStatus.OK);
    }
}
