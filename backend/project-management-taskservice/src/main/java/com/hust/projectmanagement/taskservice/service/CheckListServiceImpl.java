package com.hust.projectmanagement.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.projectmanagement.taskservice.domain.CheckList;
import com.hust.projectmanagement.taskservice.repository.CheckListRepository;

@Service
@Transactional
public class CheckListServiceImpl implements CheckListService{
	@Autowired
	private CheckListRepository checkListRepository;
	@Override
	public CheckList add(CheckList checkList) {
		// TODO Auto-generated method stub
		return this.checkListRepository.save(checkList);
	}

	@Override
	public CheckList update(CheckList checkList) {
		// TODO Auto-generated method stub
		return this.checkListRepository.saveAndFlush(checkList);
	}

	@Override
	public CheckList delete(CheckList checkList) {
		// TODO Auto-generated method stub
		this.checkListRepository.delete(checkList);
		return checkList;
	}

}
