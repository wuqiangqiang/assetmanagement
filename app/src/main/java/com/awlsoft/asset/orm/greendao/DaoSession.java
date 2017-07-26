package com.awlsoft.asset.orm.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.awlsoft.asset.model.entry.AssetAddBean;
import com.awlsoft.asset.model.entry.AssetFound;
import com.awlsoft.asset.model.entry.response.AdminResponse;
import com.awlsoft.asset.model.entry.response.AllocationTaskResponse;
import com.awlsoft.asset.model.entry.response.AssetBatchResponse;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.BorrowTaskResponse;
import com.awlsoft.asset.model.entry.response.BrandResponse;
import com.awlsoft.asset.model.entry.response.BreakageTaskResponse;
import com.awlsoft.asset.model.entry.response.CategoryGbResponse;
import com.awlsoft.asset.model.entry.response.CategoryResponse;
import com.awlsoft.asset.model.entry.response.DepartmentResponse;
import com.awlsoft.asset.model.entry.response.InventoryResponse;
import com.awlsoft.asset.model.entry.response.KeeperResponse;
import com.awlsoft.asset.model.entry.response.ModelResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;
import com.awlsoft.asset.model.entry.response.ReturnTaskResponse;
import com.awlsoft.asset.model.entry.response.ScrapTaskResponse;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.model.entry.response.WorkareaResponse;
import com.awlsoft.asset.model.entry.TaskAssetFound;

import com.awlsoft.asset.orm.greendao.AssetAddBeanDao;
import com.awlsoft.asset.orm.greendao.AssetFoundDao;
import com.awlsoft.asset.orm.greendao.AdminResponseDao;
import com.awlsoft.asset.orm.greendao.AllocationTaskResponseDao;
import com.awlsoft.asset.orm.greendao.AssetBatchResponseDao;
import com.awlsoft.asset.orm.greendao.AssetResponseDao;
import com.awlsoft.asset.orm.greendao.BorrowTaskResponseDao;
import com.awlsoft.asset.orm.greendao.BrandResponseDao;
import com.awlsoft.asset.orm.greendao.BreakageTaskResponseDao;
import com.awlsoft.asset.orm.greendao.CategoryGbResponseDao;
import com.awlsoft.asset.orm.greendao.CategoryResponseDao;
import com.awlsoft.asset.orm.greendao.DepartmentResponseDao;
import com.awlsoft.asset.orm.greendao.InventoryResponseDao;
import com.awlsoft.asset.orm.greendao.KeeperResponseDao;
import com.awlsoft.asset.orm.greendao.ModelResponseDao;
import com.awlsoft.asset.orm.greendao.ReceiveTaskResponseDao;
import com.awlsoft.asset.orm.greendao.ReturnTaskResponseDao;
import com.awlsoft.asset.orm.greendao.ScrapTaskResponseDao;
import com.awlsoft.asset.orm.greendao.UserResponseDao;
import com.awlsoft.asset.orm.greendao.WorkareaResponseDao;
import com.awlsoft.asset.orm.greendao.TaskAssetFoundDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig assetAddBeanDaoConfig;
    private final DaoConfig assetFoundDaoConfig;
    private final DaoConfig adminResponseDaoConfig;
    private final DaoConfig allocationTaskResponseDaoConfig;
    private final DaoConfig assetBatchResponseDaoConfig;
    private final DaoConfig assetResponseDaoConfig;
    private final DaoConfig borrowTaskResponseDaoConfig;
    private final DaoConfig brandResponseDaoConfig;
    private final DaoConfig breakageTaskResponseDaoConfig;
    private final DaoConfig categoryGbResponseDaoConfig;
    private final DaoConfig categoryResponseDaoConfig;
    private final DaoConfig departmentResponseDaoConfig;
    private final DaoConfig inventoryResponseDaoConfig;
    private final DaoConfig keeperResponseDaoConfig;
    private final DaoConfig modelResponseDaoConfig;
    private final DaoConfig receiveTaskResponseDaoConfig;
    private final DaoConfig returnTaskResponseDaoConfig;
    private final DaoConfig scrapTaskResponseDaoConfig;
    private final DaoConfig userResponseDaoConfig;
    private final DaoConfig workareaResponseDaoConfig;
    private final DaoConfig taskAssetFoundDaoConfig;

    private final AssetAddBeanDao assetAddBeanDao;
    private final AssetFoundDao assetFoundDao;
    private final AdminResponseDao adminResponseDao;
    private final AllocationTaskResponseDao allocationTaskResponseDao;
    private final AssetBatchResponseDao assetBatchResponseDao;
    private final AssetResponseDao assetResponseDao;
    private final BorrowTaskResponseDao borrowTaskResponseDao;
    private final BrandResponseDao brandResponseDao;
    private final BreakageTaskResponseDao breakageTaskResponseDao;
    private final CategoryGbResponseDao categoryGbResponseDao;
    private final CategoryResponseDao categoryResponseDao;
    private final DepartmentResponseDao departmentResponseDao;
    private final InventoryResponseDao inventoryResponseDao;
    private final KeeperResponseDao keeperResponseDao;
    private final ModelResponseDao modelResponseDao;
    private final ReceiveTaskResponseDao receiveTaskResponseDao;
    private final ReturnTaskResponseDao returnTaskResponseDao;
    private final ScrapTaskResponseDao scrapTaskResponseDao;
    private final UserResponseDao userResponseDao;
    private final WorkareaResponseDao workareaResponseDao;
    private final TaskAssetFoundDao taskAssetFoundDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        assetAddBeanDaoConfig = daoConfigMap.get(AssetAddBeanDao.class).clone();
        assetAddBeanDaoConfig.initIdentityScope(type);

        assetFoundDaoConfig = daoConfigMap.get(AssetFoundDao.class).clone();
        assetFoundDaoConfig.initIdentityScope(type);

        adminResponseDaoConfig = daoConfigMap.get(AdminResponseDao.class).clone();
        adminResponseDaoConfig.initIdentityScope(type);

        allocationTaskResponseDaoConfig = daoConfigMap.get(AllocationTaskResponseDao.class).clone();
        allocationTaskResponseDaoConfig.initIdentityScope(type);

        assetBatchResponseDaoConfig = daoConfigMap.get(AssetBatchResponseDao.class).clone();
        assetBatchResponseDaoConfig.initIdentityScope(type);

        assetResponseDaoConfig = daoConfigMap.get(AssetResponseDao.class).clone();
        assetResponseDaoConfig.initIdentityScope(type);

        borrowTaskResponseDaoConfig = daoConfigMap.get(BorrowTaskResponseDao.class).clone();
        borrowTaskResponseDaoConfig.initIdentityScope(type);

        brandResponseDaoConfig = daoConfigMap.get(BrandResponseDao.class).clone();
        brandResponseDaoConfig.initIdentityScope(type);

        breakageTaskResponseDaoConfig = daoConfigMap.get(BreakageTaskResponseDao.class).clone();
        breakageTaskResponseDaoConfig.initIdentityScope(type);

        categoryGbResponseDaoConfig = daoConfigMap.get(CategoryGbResponseDao.class).clone();
        categoryGbResponseDaoConfig.initIdentityScope(type);

        categoryResponseDaoConfig = daoConfigMap.get(CategoryResponseDao.class).clone();
        categoryResponseDaoConfig.initIdentityScope(type);

        departmentResponseDaoConfig = daoConfigMap.get(DepartmentResponseDao.class).clone();
        departmentResponseDaoConfig.initIdentityScope(type);

        inventoryResponseDaoConfig = daoConfigMap.get(InventoryResponseDao.class).clone();
        inventoryResponseDaoConfig.initIdentityScope(type);

        keeperResponseDaoConfig = daoConfigMap.get(KeeperResponseDao.class).clone();
        keeperResponseDaoConfig.initIdentityScope(type);

        modelResponseDaoConfig = daoConfigMap.get(ModelResponseDao.class).clone();
        modelResponseDaoConfig.initIdentityScope(type);

        receiveTaskResponseDaoConfig = daoConfigMap.get(ReceiveTaskResponseDao.class).clone();
        receiveTaskResponseDaoConfig.initIdentityScope(type);

        returnTaskResponseDaoConfig = daoConfigMap.get(ReturnTaskResponseDao.class).clone();
        returnTaskResponseDaoConfig.initIdentityScope(type);

        scrapTaskResponseDaoConfig = daoConfigMap.get(ScrapTaskResponseDao.class).clone();
        scrapTaskResponseDaoConfig.initIdentityScope(type);

        userResponseDaoConfig = daoConfigMap.get(UserResponseDao.class).clone();
        userResponseDaoConfig.initIdentityScope(type);

        workareaResponseDaoConfig = daoConfigMap.get(WorkareaResponseDao.class).clone();
        workareaResponseDaoConfig.initIdentityScope(type);

        taskAssetFoundDaoConfig = daoConfigMap.get(TaskAssetFoundDao.class).clone();
        taskAssetFoundDaoConfig.initIdentityScope(type);

        assetAddBeanDao = new AssetAddBeanDao(assetAddBeanDaoConfig, this);
        assetFoundDao = new AssetFoundDao(assetFoundDaoConfig, this);
        adminResponseDao = new AdminResponseDao(adminResponseDaoConfig, this);
        allocationTaskResponseDao = new AllocationTaskResponseDao(allocationTaskResponseDaoConfig, this);
        assetBatchResponseDao = new AssetBatchResponseDao(assetBatchResponseDaoConfig, this);
        assetResponseDao = new AssetResponseDao(assetResponseDaoConfig, this);
        borrowTaskResponseDao = new BorrowTaskResponseDao(borrowTaskResponseDaoConfig, this);
        brandResponseDao = new BrandResponseDao(brandResponseDaoConfig, this);
        breakageTaskResponseDao = new BreakageTaskResponseDao(breakageTaskResponseDaoConfig, this);
        categoryGbResponseDao = new CategoryGbResponseDao(categoryGbResponseDaoConfig, this);
        categoryResponseDao = new CategoryResponseDao(categoryResponseDaoConfig, this);
        departmentResponseDao = new DepartmentResponseDao(departmentResponseDaoConfig, this);
        inventoryResponseDao = new InventoryResponseDao(inventoryResponseDaoConfig, this);
        keeperResponseDao = new KeeperResponseDao(keeperResponseDaoConfig, this);
        modelResponseDao = new ModelResponseDao(modelResponseDaoConfig, this);
        receiveTaskResponseDao = new ReceiveTaskResponseDao(receiveTaskResponseDaoConfig, this);
        returnTaskResponseDao = new ReturnTaskResponseDao(returnTaskResponseDaoConfig, this);
        scrapTaskResponseDao = new ScrapTaskResponseDao(scrapTaskResponseDaoConfig, this);
        userResponseDao = new UserResponseDao(userResponseDaoConfig, this);
        workareaResponseDao = new WorkareaResponseDao(workareaResponseDaoConfig, this);
        taskAssetFoundDao = new TaskAssetFoundDao(taskAssetFoundDaoConfig, this);

        registerDao(AssetAddBean.class, assetAddBeanDao);
        registerDao(AssetFound.class, assetFoundDao);
        registerDao(AdminResponse.class, adminResponseDao);
        registerDao(AllocationTaskResponse.class, allocationTaskResponseDao);
        registerDao(AssetBatchResponse.class, assetBatchResponseDao);
        registerDao(AssetResponse.class, assetResponseDao);
        registerDao(BorrowTaskResponse.class, borrowTaskResponseDao);
        registerDao(BrandResponse.class, brandResponseDao);
        registerDao(BreakageTaskResponse.class, breakageTaskResponseDao);
        registerDao(CategoryGbResponse.class, categoryGbResponseDao);
        registerDao(CategoryResponse.class, categoryResponseDao);
        registerDao(DepartmentResponse.class, departmentResponseDao);
        registerDao(InventoryResponse.class, inventoryResponseDao);
        registerDao(KeeperResponse.class, keeperResponseDao);
        registerDao(ModelResponse.class, modelResponseDao);
        registerDao(ReceiveTaskResponse.class, receiveTaskResponseDao);
        registerDao(ReturnTaskResponse.class, returnTaskResponseDao);
        registerDao(ScrapTaskResponse.class, scrapTaskResponseDao);
        registerDao(UserResponse.class, userResponseDao);
        registerDao(WorkareaResponse.class, workareaResponseDao);
        registerDao(TaskAssetFound.class, taskAssetFoundDao);
    }
    
    public void clear() {
        assetAddBeanDaoConfig.clearIdentityScope();
        assetFoundDaoConfig.clearIdentityScope();
        adminResponseDaoConfig.clearIdentityScope();
        allocationTaskResponseDaoConfig.clearIdentityScope();
        assetBatchResponseDaoConfig.clearIdentityScope();
        assetResponseDaoConfig.clearIdentityScope();
        borrowTaskResponseDaoConfig.clearIdentityScope();
        brandResponseDaoConfig.clearIdentityScope();
        breakageTaskResponseDaoConfig.clearIdentityScope();
        categoryGbResponseDaoConfig.clearIdentityScope();
        categoryResponseDaoConfig.clearIdentityScope();
        departmentResponseDaoConfig.clearIdentityScope();
        inventoryResponseDaoConfig.clearIdentityScope();
        keeperResponseDaoConfig.clearIdentityScope();
        modelResponseDaoConfig.clearIdentityScope();
        receiveTaskResponseDaoConfig.clearIdentityScope();
        returnTaskResponseDaoConfig.clearIdentityScope();
        scrapTaskResponseDaoConfig.clearIdentityScope();
        userResponseDaoConfig.clearIdentityScope();
        workareaResponseDaoConfig.clearIdentityScope();
        taskAssetFoundDaoConfig.clearIdentityScope();
    }

    public AssetAddBeanDao getAssetAddBeanDao() {
        return assetAddBeanDao;
    }

    public AssetFoundDao getAssetFoundDao() {
        return assetFoundDao;
    }

    public AdminResponseDao getAdminResponseDao() {
        return adminResponseDao;
    }

    public AllocationTaskResponseDao getAllocationTaskResponseDao() {
        return allocationTaskResponseDao;
    }

    public AssetBatchResponseDao getAssetBatchResponseDao() {
        return assetBatchResponseDao;
    }

    public AssetResponseDao getAssetResponseDao() {
        return assetResponseDao;
    }

    public BorrowTaskResponseDao getBorrowTaskResponseDao() {
        return borrowTaskResponseDao;
    }

    public BrandResponseDao getBrandResponseDao() {
        return brandResponseDao;
    }

    public BreakageTaskResponseDao getBreakageTaskResponseDao() {
        return breakageTaskResponseDao;
    }

    public CategoryGbResponseDao getCategoryGbResponseDao() {
        return categoryGbResponseDao;
    }

    public CategoryResponseDao getCategoryResponseDao() {
        return categoryResponseDao;
    }

    public DepartmentResponseDao getDepartmentResponseDao() {
        return departmentResponseDao;
    }

    public InventoryResponseDao getInventoryResponseDao() {
        return inventoryResponseDao;
    }

    public KeeperResponseDao getKeeperResponseDao() {
        return keeperResponseDao;
    }

    public ModelResponseDao getModelResponseDao() {
        return modelResponseDao;
    }

    public ReceiveTaskResponseDao getReceiveTaskResponseDao() {
        return receiveTaskResponseDao;
    }

    public ReturnTaskResponseDao getReturnTaskResponseDao() {
        return returnTaskResponseDao;
    }

    public ScrapTaskResponseDao getScrapTaskResponseDao() {
        return scrapTaskResponseDao;
    }

    public UserResponseDao getUserResponseDao() {
        return userResponseDao;
    }

    public WorkareaResponseDao getWorkareaResponseDao() {
        return workareaResponseDao;
    }

    public TaskAssetFoundDao getTaskAssetFoundDao() {
        return taskAssetFoundDao;
    }

}