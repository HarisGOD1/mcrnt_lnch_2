package ru.thegod.userRepositories

import jakarta.inject.Singleton

@Singleton
class GitRepositoryService(private val repository: GitRRepository) {

    fun getAllGitRepositories():List<GitRepositoryEntityResponseDTO>{
        return repository.findAll().map { entity -> entity.toResponseDTO() }
    }

    fun getGitRepositoryById(id:Long): GitRepositoryEntityResponseDTO?{
        try {
            val entity = repository.findById(id).get()
            return entity.toResponseDTO()
        }
        catch (e:NoSuchElementException){
            return null
        }
    }

    fun saveGitRepository(gitRepositoryEntityRequestDTO:GitRepositoryEntityRequestDTO):GitRepositoryEntityResponseDTO{
        return repository.save(
            GitRepositoryEntity(gitRepositoryEntityRequestDTO.gitRepositoryName,
                gitRepositoryEntityRequestDTO.gitOwnerName,
                gitRepositoryEntityRequestDTO.publicity,
                gitRepositoryEntityRequestDTO.repositoryDescription)
        ).toResponseDTO()
    }

    fun addMemberInGitRepository(args: GitRepositoryAddMemberListRequestDTO): GitRepositoryEntityResponseDTO?{
        var entity = repository.findById(args.repositoryId).get()
        for (e in args.memberNames){
            if(!entity.membersNames.contains(e)) entity.membersNames.add(e)
        }
        repository.update(entity)
        return repository.findById(args.repositoryId).get().toResponseDTO()
    }

}