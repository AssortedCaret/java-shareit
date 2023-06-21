package ru.practicum.shareit.item.comment;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {
    private final CommentMapper commentMapper = new CommentMapper();
    private final EasyRandom easyRandom = new EasyRandom();

    @Test
    void makeComment() {
        CommentDto commentDto = easyRandom.nextObject(CommentDto.class);
        Comment comment = commentMapper.makeComment(commentDto);
        assertEquals(comment.getId(), commentDto.getId());
    }

    @Test
    void makeCommentDto() {
        Comment comment = easyRandom.nextObject(Comment.class);
        CommentDto commentDto = commentMapper.makeCommentDto(comment);
        assertEquals(comment.getId(), commentDto.getId());
    }

    @Test
    void makeCommentDtoList() {
        Comment comment = easyRandom.nextObject(Comment.class);
        List<Comment> list = new ArrayList();
        list.add(comment);
        List<CommentDto> listDto = commentMapper.makeCommentDtoList(list);
        assertEquals(list.size(), listDto.size());
    }
}