package com.finfellows.domain.bookmark.application;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import com.finfellows.domain.bookmark.domain.FinancialProductBookmark;
import com.finfellows.domain.bookmark.domain.PolicyInfoBookmark;
import com.finfellows.domain.bookmark.domain.repository.CmaBookmarkRepository;
import com.finfellows.domain.bookmark.domain.repository.FinancialProductBookmarkRepository;
import com.finfellows.domain.bookmark.domain.repository.PolicyInfoBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkCleanupService {

    private final FinancialProductBookmarkRepository financialProductBookmarkRepository;
    private final CmaBookmarkRepository cmaBookmarkRepository;

    private final PolicyInfoBookmarkRepository policyInfoBookmarkRepository;

    @Transactional
    public void deleteAllBookmarks() {
        // Delete all FinancialProductBookmarks
        List<FinancialProductBookmark> financialProductBookmarks = financialProductBookmarkRepository.findAll();
        financialProductBookmarkRepository.deleteAll(financialProductBookmarks); // Using deleteInBatch or deleteAll to remove all entries

        // Delete all CmaBookmarks
        List<CmaBookmark> cmaBookmarks = cmaBookmarkRepository.findAll();
        cmaBookmarkRepository.deleteAll(cmaBookmarks); // Similarly, using deleteInBatch or deleteAll to remove all entries
    }

    @Transactional
    public void deleteAllBookmarksPolicyInfo() {
        List<PolicyInfoBookmark> policyInfoBookmarks = policyInfoBookmarkRepository.findAll();
        policyInfoBookmarkRepository.deleteAll(policyInfoBookmarks);

    }
}
