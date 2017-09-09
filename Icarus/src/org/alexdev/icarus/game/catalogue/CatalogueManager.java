package org.alexdev.icarus.game.catalogue;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.catalogue.CatalogueDao;
import org.alexdev.icarus.dao.mysql.catalogue.TargetedOfferDao;
import org.alexdev.icarus.game.catalogue.targetedoffer.TargetedOffer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CatalogueManager {

    private static List<CatalogueTab> parentTabs;
    private static Map<Integer, List<CatalogueTab>> childTabs;

    private static List<CataloguePage> pages;
    private static List<CatalogueItem> items;
    
    private static TargetedOffer offer;

    public static void load() {
        loadCataloguePages();
        loadCatalogueItems();

        offer = TargetedOfferDao.getOffer();
        parentTabs = CatalogueDao.getCatalogTabs(-1);
        childTabs = Maps.newHashMap();

        for (CatalogueTab parent : parentTabs) {
            loadCatalogueTabs(parent, parent.getID());
        }
    }

    public static void loadCatalogueTabs(CatalogueTab tab, int parent_id) {

        List<CatalogueTab> child = CatalogueDao.getCatalogTabs(tab.getID());

        if (child.size() > 0) { 

            for (CatalogueTab parent_tab : child) {
                tab.getChildTabs().add(parent_tab);
                loadCatalogueTabs(parent_tab, parent_tab.getID());
            }
        }
    }

    private static void loadCataloguePages() {
        pages = CatalogueDao.getCataloguePages();
    }


    private static void loadCatalogueItems() {
        items = CatalogueDao.getCatalogueItems();
    }

    public static List<CatalogueTab> getParentTabs(int rank) {
        return parentTabs.stream().filter(tab -> tab.getMinRank() <= rank).collect(Collectors.toList());
    }

    public static List<CatalogueTab> getChildTabs(int parentID, int rank) {
        return childTabs.get(parentID).stream().filter(tab -> tab.getMinRank() <= rank).collect(Collectors.toList());
    }

    public static CataloguePage getPage(int pageID) {

        Optional<CataloguePage> cataloguePage = pages.stream().filter(page -> page.getID() == pageID).findFirst();

        if (cataloguePage.isPresent()) {
            return cataloguePage.get();
        } else {
            return null;
        }

    }

    public static List<CatalogueItem> getPageItems(int pageID) {

        try {
            return items.stream().filter(item -> item.getPageID() == pageID).collect(Collectors.toList());
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    public static CatalogueItem getItem(int itemID) {

        Optional<CatalogueItem> catalogueItem = items.stream().filter(item -> item.getID() == itemID).findFirst();

        if (catalogueItem.isPresent()) {
            return catalogueItem.get();
        } else {
            return null;
        }

    }

    public List<CatalogueItem> getItems() {
        return items;
    }

    public static TargetedOffer getOffer() {
        return offer;
    }
}
