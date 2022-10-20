package com.prog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CachePut;
import com.prog.entity.Medicine;
import com.prog.repository.MediRepo;
import org.springframework.beans.BeanUtils;

@Service
public class MediService 
{
	@Autowired
	private MediRepo repo;
	
	@CachePut(value="empCacheSpace",key="#result.id")
	public Medicine addMedicine(Medicine medi)
	{
		Medicine medicine = repo.save(medi);
		Medicine medicine2 = new Medicine();
		BeanUtils.copyProperties(medicine, medicine2);
		return medicine2;
	}
	
	public List<Medicine> serachMedicine(String name)
	{
		return repo.findByNameLike("%"+name+"%");
	}
	
	public void updateMedicine(Medicine medi)
	{
		repo.save(medi);
	}
	
	
	public List<Medicine> getAllMedicines()
	{
		return repo.findAll();
	}

	
	@Cacheable(value="empCacheSpace", key="#id")
	public Medicine getMediById(int id)
	{
		Optional<Medicine> medi = repo.findById(id);
		if(medi.isPresent())
			return medi.get();
		return null;
	}

	public void deleteMedicine(int id)
	{
		repo.deleteById(id);
	}

	public Medicine getMedicineByName(String name) {
		
		return repo.getMedicineByName(name);
	}
	
	public Page<Medicine> findPaginated(int pageNo, int pageSize)
	{
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return repo.findAll(pageable);
	}
	
	
	
}
