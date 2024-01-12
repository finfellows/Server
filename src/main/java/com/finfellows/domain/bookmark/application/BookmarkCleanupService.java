package com.finfellows.domain.bookmark.application;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import com.finfellows.domain.bookmark.domain.FinancialProductBookmark;
import com.finfellows.domain.bookmark.domain.repository.CmaBookmarkRepository;
import com.finfellows.domain.bookmark.domain.repository.FinancialProductBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkCleanupService {

    private final FinancialProductBookmarkRepository financialProductBookmarkRepository;
    private final CmaBookmarkRepository cmaBookmarkRepository;

    @Transactional
    public void deleteAllBookmarks() {
        // Delete all FinancialProductBookmarks
        List<FinancialProductBookmark> financialProductBookmarks = financialProductBookmarkRepository.findAll();
        financialProductBookmarkRepository.deleteAll(financialProductBookmarks); // Using deleteInBatch or deleteAll to remove all entries

        // Delete all CmaBookmarks
        List<CmaBookmark> cmaBookmarks = cmaBookmarkRepository.findAll();
        cmaBookmarkRepository.deleteAll(cmaBookmarks); // Similarly, using deleteInBatch or deleteAll to remove all entries
    }
}
