package com.lawfirm.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.personnel.dto.person.CreatePersonDTO;
import com.lawfirm.model.personnel.dto.person.UpdatePersonDTO;
import com.lawfirm.model.personnel.entity.Person;
import com.lawfirm.model.personnel.service.PersonService;
import com.lawfirm.model.personnel.vo.PersonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.function.Function;

/**
 * 人员服务实现类
 * 基于对personnel-model的深入理解实现的PersonService
 * 使用内存存储，提供基础的人员管理功能
 */
@Slf4j
@Service("personService")
@ConditionalOnProperty(prefix = "personnel", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PersonServiceImpl implements PersonService {

    // 内存存储
    private final Map<Long, PersonVO> personStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // =================== PersonService 自定义方法实现 ===================

    @Override
    public Long createPerson(CreatePersonDTO createDTO) {
        log.info("创建人员: {}", createDTO.getName());
        
        Long personId = idGenerator.getAndIncrement();
        PersonVO personVO = new PersonVO();
        
        // 设置基础信息
        personVO.setId(personId);
        personVO.setName(createDTO.getName());
        personVO.setEnglishName(createDTO.getEnglishName());
        personVO.setGender(createDTO.getGender());
        personVO.setBirthDate(createDTO.getBirthDate());
        personVO.setIdType(createDTO.getIdType());
        personVO.setIdNumber(createDTO.getIdNumber());
        personVO.setMobile(createDTO.getMobile());
        personVO.setEmail(createDTO.getEmail());
        personVO.setType(createDTO.getType());
        personVO.setFirmId(createDTO.getFirmId());
        personVO.setEmergencyContact(createDTO.getEmergencyContact());
        personVO.setEmergencyMobile(createDTO.getEmergencyMobile());
        personVO.setPhotoUrl(createDTO.getPhotoUrl());
        
        // 设置默认状态
        personVO.setStatus(1); // 启用
        personVO.setCreateTime(LocalDateTime.now());
        
        personStore.put(personId, personVO);
        log.info("人员创建成功，ID: {}", personId);
        
        return personId;
    }

    @Override
    public boolean updatePerson(UpdatePersonDTO updateDTO) {
        log.info("更新人员: {}", updateDTO.getId());
        PersonVO person = personStore.get(updateDTO.getId());
        if (person == null) {
            return false;
        }
        
        // 更新非空字段
        if (updateDTO.getName() != null) person.setName(updateDTO.getName());
        if (updateDTO.getEnglishName() != null) person.setEnglishName(updateDTO.getEnglishName());
        if (updateDTO.getGender() != null) person.setGender(updateDTO.getGender());
        if (updateDTO.getBirthDate() != null) person.setBirthDate(updateDTO.getBirthDate());
        if (updateDTO.getIdType() != null) person.setIdType(updateDTO.getIdType());
        if (updateDTO.getIdNumber() != null) person.setIdNumber(updateDTO.getIdNumber());
        if (updateDTO.getMobile() != null) person.setMobile(updateDTO.getMobile());
        if (updateDTO.getEmail() != null) person.setEmail(updateDTO.getEmail());
        if (updateDTO.getType() != null) person.setType(updateDTO.getType());
        if (updateDTO.getFirmId() != null) person.setFirmId(updateDTO.getFirmId());
        if (updateDTO.getEmergencyContact() != null) person.setEmergencyContact(updateDTO.getEmergencyContact());
        if (updateDTO.getEmergencyMobile() != null) person.setEmergencyMobile(updateDTO.getEmergencyMobile());
        if (updateDTO.getPhotoUrl() != null) person.setPhotoUrl(updateDTO.getPhotoUrl());
        if (updateDTO.getStatus() != null) person.setStatus(updateDTO.getStatus());
        
        person.setUpdateTime(LocalDateTime.now());
        return true;
    }

    @Override
    public PersonVO getPersonDetail(Long id) {
        return personStore.get(id);
    }

    @Override
    public List<PersonVO> listPersons(Long firmId, Integer type) {
        return personStore.values().stream()
                .filter(person -> (firmId == null || firmId.equals(person.getFirmId())) &&
                                (type == null || (person.getType() != null && type.equals(person.getType().getValue()))))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PersonVO> pagePersons(Page<Person> page, Long firmId, Integer type, String keyword) {
        List<PersonVO> filtered = listPersons(firmId, type);
        if (keyword != null && !keyword.trim().isEmpty()) {
            filtered = filtered.stream()
                    .filter(p -> p.getName().contains(keyword) || 
                               (p.getPersonCode() != null && p.getPersonCode().contains(keyword)) ||
                               (p.getMobile() != null && p.getMobile().contains(keyword)))
                    .collect(Collectors.toList());
        }
        
        Page<PersonVO> result = new Page<>();
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setTotal(filtered.size());
        
        int start = (int) ((page.getCurrent() - 1) * page.getSize());
        int end = Math.min(start + (int) page.getSize(), filtered.size());
        
        if (start < filtered.size()) {
            result.setRecords(filtered.subList(start, end));
        } else {
            result.setRecords(new ArrayList<>());
        }
        
        return result;
    }

    @Override
    public boolean deletePerson(Long id) {
        return personStore.remove(id) != null;
    }

    @Override
    public boolean deletePersonBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return true;
        ids.forEach(personStore::remove);
        return true;
    }

    @Override
    public boolean enablePerson(Long id) {
        PersonVO person = personStore.get(id);
        if (person == null) return false;
        person.setStatus(1);
        return true;
    }

    @Override
    public boolean disablePerson(Long id) {
        PersonVO person = personStore.get(id);
        if (person == null) return false;
        person.setStatus(0);
        return true;
    }

    @Override
    public boolean checkPersonCodeExists(String personCode, Long excludeId) {
        return personStore.values().stream()
                .anyMatch(p -> personCode.equals(p.getPersonCode()) && 
                             (excludeId == null || !excludeId.equals(p.getId())));
    }

    @Override
    public PersonVO getPersonByCode(String personCode) {
        return personStore.values().stream()
                .filter(p -> personCode.equals(p.getPersonCode()))
                .findFirst()
                .orElse(null);
    }

    // =================== BaseService<Person> 方法实现 ===================

    @Override
    public boolean save(Person entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.getAndIncrement());
        }
        return true;
    }

    @Override
    public boolean saveBatch(List<Person> entityList) {
        return true;
    }

    @Override
    public boolean update(Person entity) {
        return updateById(entity);
    }

    @Override
    public boolean updateBatch(List<Person> entityList) {
        return true;
    }

    @Override
    public boolean remove(Long id) {
        return personStore.remove(id) != null;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        if (ids == null) return true;
        ids.forEach(personStore::remove);
        return true;
    }

    @Override
    public Person getById(Long id) {
        PersonVO vo = personStore.get(id);
        if (vo == null) return null;
        
        // 转换为Person实体
        Person person = new Person();
        person.setId(vo.getId());
        person.setPersonCode(vo.getPersonCode());
        person.setName(vo.getName());
        person.setEnglishName(vo.getEnglishName());
        person.setGender(vo.getGender());
        person.setBirthDate(vo.getBirthDate());
        person.setIdType(vo.getIdType());
        person.setIdNumber(vo.getIdNumber());
        person.setMobile(vo.getMobile());
        person.setEmail(vo.getEmail());
        person.setType(vo.getType());
        person.setFirmId(vo.getFirmId());
        return person;
    }

    @Override
    public List<Person> list(QueryWrapper<Person> wrapper) {
        return new ArrayList<>();
    }

    @Override
    public Page<Person> page(Page<Person> page, QueryWrapper<Person> wrapper) {
        return page;
    }

    @Override
    public long count(QueryWrapper<Person> wrapper) {
        return personStore.size();
    }

    @Override
    public boolean exists(QueryWrapper<Person> wrapper) {
        return !personStore.isEmpty();
    }

    @Override
    public Long getCurrentTenantId() {
        return 1L; // 默认租户ID
    }

    @Override
    public Long getCurrentUserId() {
        return 1L; // 默认系统用户ID
    }

    @Override
    public String getCurrentUsername() {
        return "system"; // 默认系统用户名
    }

    // =================== IService<Person> 方法实现 ===================

    @Override
    public boolean saveBatch(Collection<Person> entityList) {
        return true;
    }

    @Override
    public boolean saveOrUpdate(Person entity) {
        return entity.getId() == null ? save(entity) : updateById(entity);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Person> entityList) {
        return true;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Person> entityList, int batchSize) {
        return saveOrUpdateBatch(entityList);
    }

    @Override
    public boolean removeById(Serializable id) {
        return personStore.remove(id) != null;
    }

    @Override
    public boolean removeByMap(Map<String, Object> columnMap) {
        return true;
    }

    @Override
    public boolean remove(Wrapper<Person> queryWrapper) {
        return true;
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        if (idList != null) {
            idList.forEach(personStore::remove);
        }
        return true;
    }

    @Override
    public boolean updateById(Person entity) {
        return true;
    }

    @Override
    public boolean update(Person entity, Wrapper<Person> updateWrapper) {
        return true;
    }

    @Override
    public boolean updateBatchById(Collection<Person> entityList) {
        return true;
    }

    @Override
    public boolean updateBatchById(Collection<Person> entityList, int batchSize) {
        return updateBatchById(entityList);
    }

    @Override
    public List<Person> listByIds(Collection<? extends Serializable> idList) {
        return new ArrayList<>();
    }

    @Override
    public List<Person> listByMap(Map<String, Object> columnMap) {
        return new ArrayList<>();
    }

    @Override
    public Person getOne(Wrapper<Person> queryWrapper) {
        return null;
    }

    @Override
    public Person getOne(Wrapper<Person> queryWrapper, boolean throwEx) {
        return getOne(queryWrapper);
    }

    @Override
    public java.util.Optional<Person> getOneOpt(Wrapper<Person> queryWrapper, boolean throwEx) {
        return java.util.Optional.ofNullable(getOne(queryWrapper));
    }

    @Override
    public Map<String, Object> getMap(Wrapper<Person> queryWrapper) {
        return new java.util.HashMap<>();
    }

    @Override
    public <V> V getObj(Wrapper<Person> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public long count() {
        return personStore.size();
    }

    @Override
    public long count(Wrapper<Person> queryWrapper) {
        return personStore.size();
    }

    @Override
    public List<Person> list() {
        return new ArrayList<>();
    }

    @Override
    public List<Map<String, Object>> listMaps() {
        return new ArrayList<>();
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<Person> queryWrapper) {
        return new ArrayList<>();
    }

    @Override
    public <V> List<V> listObjs(Function<? super Object, V> mapper) {
        return new ArrayList<>();
    }

    @Override
    public <E> List<E> listObjs(Wrapper<Person> queryWrapper) {
        return new ArrayList<>();
    }

    @Override
    public <V> List<V> listObjs(Wrapper<Person> queryWrapper, Function<? super Object, V> mapper) {
        return new ArrayList<>();
    }

    @Override
    public BaseMapper<Person> getBaseMapper() {
        return null; // 内存存储，不使用Mapper
    }

    @Override
    public Class<Person> getEntityClass() {
        return Person.class;
    }

    @Override
    public boolean saveBatch(Collection<Person> entityList, int batchSize) {
        return saveBatch(entityList);
    }

    @Override
    public <E> List<E> listObjs() {
        return new ArrayList<>();
    }
} 