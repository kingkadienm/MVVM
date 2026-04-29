package com.wangzs.module.ai.bean

/**
 * @Classname RequestBean
 * @Description
 * @Date 2025/9/19 14:34
 * @Created by wangzs
 */
data class RequestBean(
    val companyInfo: CompanyInfo,
    val positionInfo: PositionInfo,
    val resumeInfo: ResumeInfo
)

data class CompanyInfo(
    val companyDescription: String,
    val companyId: Int,
    val companyIndustry: String,
    val companyName: String,
    val companyScale: String
)

data class PositionInfo(
    val candidateType: String,
    val companyId: Int,
    val location: String,
    val nameWithLevel: Any,
    val positionDescription: String,
    val positionExplain: Any,
    val positionId: Int,
    val positionName: String,
    val positionSource: String,
    val requirement: Any,
    val responsibility: Any,
    val salaryRange: String,
    val similarityScore: Int,
    val userId: Int
)

data class ResumeInfo(
    val resumeContent: Any,
    val resumeId: Int,
    val resumeName: String,
    val type: String
)