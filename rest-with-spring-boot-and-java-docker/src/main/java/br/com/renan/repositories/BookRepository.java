package br.com.renan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.renan.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {}