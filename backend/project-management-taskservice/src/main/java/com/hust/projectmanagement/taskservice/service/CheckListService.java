package com.hust.projectmanagement.taskservice.service;

import com.hust.projectmanagement.taskservice.domain.CheckList;

public interface CheckListService {
	CheckList add(CheckList checkList);
	CheckList update(CheckList checkList);
	CheckList delete(CheckList checkList);
}
